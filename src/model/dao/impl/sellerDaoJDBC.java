package model.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class sellerDaoJDBC implements SellerDao {

	private  Connection connection;
	
	public sellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Seller seller) {
			String sql = "Insert into seller (name, email, birthDate,BaseSalary,departmentid) values (?,?,?,?,?) ";
					 
			 try (PreparedStatement pStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))  {
				  
					 pStatement.setString(1, seller.getName());
					 pStatement.setString(2, seller.getEmail());
					 pStatement.setDate(3, java.sql.Date.valueOf(seller.getBirthLocalDate()));
					 pStatement.setDouble(4, seller.getBaseSalary());
					 pStatement.setInt(5, seller.getDepartment().getId());
				 
				 int linhasAfetadas = pStatement.executeUpdate();
				 
				 if (linhasAfetadas > 0) {
					 ResultSet rs = pStatement.getGeneratedKeys();
					 if (rs.next()) {
						int id = rs.getInt(1);
						seller.setId(id);
					}
					 
					 System.out.println("Inserção bem sucedida! Linhas Afetadas: " + linhasAfetadas);
				}
				 
				 else {
					throw new DbException("Unexpected error! no rows afected!");
				}
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		finally {
		
		}
	}

	@Override
	public void update(Seller seller) {
		String sql = "UPDATE seller SET name = ?, email = ?, birthDate = ?, BaseSalary = ?, departmentid = ? where id = ?";
		
		try { 
				PreparedStatement pStatement = connection.prepareStatement(sql);
				pStatement.setString(1, seller.getName());
				pStatement.setString(2, seller.getEmail());
				pStatement.setDate(3, java.sql.Date.valueOf( seller.getBirthLocalDate()));
				pStatement.setDouble(4, seller.getBaseSalary());
				pStatement.setInt(5, seller.getDepartment().getId());
				pStatement.setInt(6, seller.getId());
				
				int linhasAfetadas = pStatement.executeUpdate();
		        System.out.println("Atualização concluída! Linhas afetadas: " + linhasAfetadas);
		        DB.closePreparedStatement(pStatement);
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletById(Integer id) {
			String sql = "DELETE FROM seller  where id = ?";
			try { 
				PreparedStatement pStatement = connection.prepareStatement(sql);
				pStatement.setInt(1, id);
				
			
			int linhasAfetadas = pStatement.executeUpdate();
			System.out.println("Exclusão concluída! Linhas afetadas: " + linhasAfetadas);
	        
	        DB.closePreparedStatement(pStatement);
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	@Override
	public Seller findById(Integer id) {
		 PreparedStatement pStatement =null;
		 ResultSet resultSet = null;
		 String sql = ("select * ,d.Name AS DepName\r\n"
		 		+ " from seller s \r\n"
				+ "join department d on s.DepartmentId = d.id where s.Id = ? ");
		try {
			
			 pStatement = connection.prepareStatement(sql);
			 pStatement.setInt(1, id);
			 resultSet = pStatement.executeQuery();
			
			if (resultSet.next()) {
				 Department departmentQ = instantiateDepartment(resultSet);
				 Seller obj = instantiateSeller(resultSet, departmentQ);
					 return obj ;	 
			}
			return null;
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			  DB.closePreparedStatement(pStatement);
			  DB.closeResultSet(resultSet);
			 
		}
	}

	
	@Override
	public List<Seller> findAll() { 
		
	 PreparedStatement pStatement =null;
	 ResultSet resultSet = null;
	
	 String sql = ("SELECT seller.*,department.Name as DepName\r\n"
	 		+ "FROM seller INNER JOIN department\r\n"
	 		+ "ON seller.DepartmentId = department.Id\r\n"
	 		+ "order by seller.Name; ");
	try {
		 pStatement = connection.prepareStatement(sql);
		 resultSet = pStatement.executeQuery();
		
		 List<Seller> seller = new ArrayList<Seller>();
		 Map<Integer, Department> map = new HashMap<Integer, Department>();
		 
		while (resultSet.next()) {
			Department departmentQ = map.get(resultSet.getInt("DepartmentId"));
			
			if (departmentQ == null) {
			departmentQ = instantiateDepartment(resultSet);
			map.put(resultSet.getInt("DepartmentId"), departmentQ);
			}
			
			Seller obj  = instantiateSeller(resultSet, departmentQ);
			seller.add(obj);
			
		}
		return seller;
		
	}catch (SQLException e) {
		throw new DbException(e.getMessage());
	}
	
	finally {
		  DB.closePreparedStatement(pStatement);
		  DB.closeResultSet(resultSet);
		 
	}
	}
	
	@Override
	public List<Seller> findByDepartment(Department department) {
			 PreparedStatement pStatement =null;
			 ResultSet resultSet = null;
			
			 String sql = ("SELECT seller.*,department.Name as DepName \r\n"
			 		+ "FROM seller INNER JOIN department\r\n"
			 		+ "ON seller.DepartmentId = department.Id\r\n"
			 		+ "WHERE DepartmentId = ? \r\n"
			 		+ "order by seller.Name; ");
			try {
				 pStatement = connection.prepareStatement(sql);
				 pStatement.setInt(1, department.getId());
				 resultSet = pStatement.executeQuery();
				
				 List<Seller> seller = new ArrayList<Seller>();
				 Map<Integer, Department> map = new HashMap<Integer, Department>();
				 
				while (resultSet.next()) {
					Department departmentQ = map.get(resultSet.getInt("DepartmentId"));
					
					if (departmentQ == null) {
					departmentQ = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), departmentQ);
					}
					
					Seller obj  = instantiateSeller(resultSet, departmentQ);
					seller.add(obj);
					
				}
				return seller;
				
			}catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			
			finally {
				  DB.closePreparedStatement(pStatement);
				  DB.closeResultSet(resultSet);
				  
			}
		}
	
	
	private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
	   
	    return new Seller(
	        resultSet.getInt("id"), 
	        resultSet.getString("name"), 
	        resultSet.getString("email"), 
	        resultSet.getDate("birthDate").toLocalDate(), 
	        resultSet.getDouble("baseSalary"), 
	        department
	    );
	}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		   
	    return new Department(
	        resultSet.getInt("DepartmentId"),
	        resultSet.getString("DepName")
	       );
	}

}
