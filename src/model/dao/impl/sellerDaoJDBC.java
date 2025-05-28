package model.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
						 
				try (PreparedStatement pStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {  
					 
					    pStatement.setString(1, seller.getName());
					    pStatement.setString(2, seller.getEmail());
					    pStatement.setDate(3, java.sql.Date.valueOf(seller.getBirthLocalDate()));
					    pStatement.setDouble(4, seller.getBaseSalary());
					    pStatement.setInt(5, seller.getDepartment().getId());

					    int linhasAfetadas = pStatement.executeUpdate();

					    if (linhasAfetadas > 0) {
					    	 try (ResultSet rs = pStatement.getGeneratedKeys()) { 
					                if (rs.next()) {
					                    seller.setId(rs.getInt(1));
					                }
					            }

					        System.out.println("Inserção bem sucedida! Linhas Afetadas: " + linhasAfetadas);
					    } else {
					        throw new DbException("Unexpected error! No rows affected!");
					    }

					} catch (SQLException e) {
					    e.printStackTrace();
					}
		}
	
		@Override
		public void update(Seller seller) {
			String sql = "UPDATE seller SET name = ?, email = ?, birthDate = ?, BaseSalary = ?, departmentid = ? WHERE ID = ?";
			 
			 try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
				  
					 pStatement.setString(1, seller.getName());
					 pStatement.setString(2, seller.getEmail());
					 pStatement.setDate(3, java.sql.Date.valueOf(seller.getBirthLocalDate()));
					 pStatement.setDouble(4, seller.getBaseSalary());
					 pStatement.setInt(5, seller.getDepartment().getId());
					 pStatement.setInt(6, seller.getId());
				 
				 int linhasAfetadas = pStatement.executeUpdate();
				 				 
					 System.out.println("Atualização bem sucedida! Linhas Afetadas: " + linhasAfetadas);
				
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		@Override
		public void deletById(Integer id) {
				String sql = "DELETE FROM seller  where id = ?";
				try (PreparedStatement pStatement = connection.prepareStatement(sql)){ 
					pStatement.setInt(1, id);
					
				int linhasAfetadas = pStatement.executeUpdate();
				System.out.println("Exclusão concluída! Linhas afetadas: " + linhasAfetadas);
		     	
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
	
		@Override
		public Seller findById(Integer id) {
		
			 String sql = ("select * ,d.Name AS DepName\r\n"
			 		+ " from seller s \r\n"
					+ "join department d on s.DepartmentId = d.id where s.Id = ? ");
			
			 try  (PreparedStatement pStatement = connection.prepareStatement(sql)){
				 pStatement.setInt(1, id);
				
				 try (ResultSet resultSet = pStatement.executeQuery()) { 
			            if (resultSet.next()) {
			                Department departmentQ = instantiateDepartment(resultSet);
			                return instantiateSeller(resultSet, departmentQ);
			            }
			        }

				return null;
				
			}catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	
		
		@Override
		public List<Seller> findAll() { 
		
		
		 String sql = ("SELECT seller.*,department.Name as DepName\r\n"
		 		+ "FROM seller INNER JOIN department\r\n"
		 		+ "ON seller.DepartmentId = department.Id\r\n"
		 		+ "order by seller.Name; ");
		try ( PreparedStatement pStatement = connection.prepareStatement(sql);ResultSet resultSet = pStatement.executeQuery()){
			
		
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
		
	}
		
		@Override
		public List<Seller> findByDepartment(Department department) {
			
				
				 String sql = ("SELECT seller.*,department.Name as DepName \r\n"
				 		+ "FROM seller INNER JOIN department\r\n"
				 		+ "ON seller.DepartmentId = department.Id\r\n"
				 		+ "WHERE DepartmentId = ? \r\n"
				 		+ "order by seller.Name; ");
				
				 try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
					    pStatement.setInt(1, department.getId());

					    try (ResultSet resultSet = pStatement.executeQuery()) { 
					        List<Seller> sellerList = new ArrayList<>();
					        Map<Integer, Department> map = new HashMap<>();

					        while (resultSet.next()) {
					            Department departmentQ = map.get(resultSet.getInt("DepartmentId"));

					            if (departmentQ == null) {
					                departmentQ = instantiateDepartment(resultSet);
					                map.put(resultSet.getInt("DepartmentId"), departmentQ);
					            }

					            Seller obj = instantiateSeller(resultSet, departmentQ);
					            sellerList.add(obj);
					        }
					        return sellerList;
					    }
					} catch (SQLException e) {
					    throw new DbException(e.getMessage());
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
