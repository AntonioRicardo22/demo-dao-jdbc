package model.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

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
		try {
				  connection = DB.getConnection();
				 PreparedStatement pStatement = connection.prepareStatement(sql);
					 pStatement.setString(1, seller.getName());
					 pStatement.setString(2, seller.getEmail());
					 pStatement.setDate(3, java.sql.Date.valueOf(seller.getBirthLocalDate()));
					 pStatement.setDouble(4, seller.getBaseSalary());
					 pStatement.setInt(5, seller.getDepartment().getId());
				 
				 int linhasAfetadas = pStatement.executeUpdate();
				 System.out.println("Inserção bem sucedida! Linhas Afetadas: " + linhasAfetadas);
				 
				 DB.closePreparedStatement(pStatement);
				 DB.closeConnection();
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}

	@Override
	public void update(Seller seller) {
		String sql = "UPDATE seller SET name = ?, email = ?, birthDate = ?, BaseSalary = ?, departmentid = ? where id = ?";
		
		try {  connection = DB.getConnection();
				
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
				DB.closeConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletById(Integer id) {
			String sql = "DELETE FROM seller  where id = ?";
			try {  connection = DB.getConnection();
				PreparedStatement pStatement = connection.prepareStatement(sql);
				pStatement.setInt(1, id);
				
			
			int linhasAfetadas = pStatement.executeUpdate();
			System.out.println("Exclusão concluída! Linhas afetadas: " + linhasAfetadas);
	        
	        DB.closePreparedStatement(pStatement);
			DB.closeConnection();
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	@Override
	public Seller findById(Integer id) {
		  connection = null;
		 PreparedStatement pStatement =null;
		 ResultSet resultSet = null;
		 Seller seller = null;
		 String sql = ("select * from seller s \r\n"
				+ "join department d on s.DepartmentId = d.id where s.Id = ? ");
		try {
			 connection = DB.getConnection();
			 pStatement = connection.prepareStatement(sql);
			 pStatement.setInt(1, id);
			 resultSet = pStatement.executeQuery();
			
			if (resultSet.next()) {
				Department department = new Department(
						resultSet.getInt("departmentid"), resultSet.getString("name"));
						 seller = new Seller(resultSet.getInt("id"), resultSet.getString("name"), 
								 resultSet.getString("email"),resultSet.getDate("birthDate").toLocalDate(),
								 resultSet.getDouble("baseSalary"),department);
						 return seller ;	 
			}
			return null;
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
		finally {
			  DB.closePreparedStatement(pStatement);
			  DB.closeResultSet(resultSet);
			  DB.closeConnection();
		}
	}

	
	@Override
	public List<Seller> findAll() {
		 List<Seller> list = new ArrayList<>();
		 String sql = "SELECT \r\n"
		 		+ "    seller.ID,\r\n"
		 		+ "    seller.Name,\r\n"
		 		+ "    seller.Email,\r\n"
		 		+ "    seller.BirthDate,\r\n"
		 		+ "    seller.BaseSalary,\r\n"
		 		+ "    seller.DepartmentId,\r\n"
		 		+ "    department.Name\r\n"
		 		+ "FROM seller\r\n"
		 		+ "INNER JOIN department ON seller.DepartmentId = department.Id;";
		try {
			  connection = DB.getConnection();
			 
			 PreparedStatement pStatement = connection.prepareStatement(sql);
			 ResultSet resultSet = pStatement.executeQuery();
			 
			 while (resultSet.next()) {
				 Department department = new Department(
						 resultSet.getInt("departmentid"), resultSet.getString("department.Name"));
				 
				 Seller seller = new Seller(resultSet.getInt("id"), resultSet.getString("name"), 
						 resultSet.getString("email"),resultSet.getDate("birthDate").toLocalDate(),
						 resultSet.getDouble("baseSalary"),department);
				 list.add(seller);
				 
			 }
			 
			 DB.closePreparedStatement(pStatement);
			 DB.closeResultSet(resultSet);
			 DB.closeConnection();
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		return list;
	}

}
