package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.DB;
import model.dao.DepartmentDao;
import model.entities.Department;


public class departmentDaoJDBC implements DepartmentDao{
	
	
	private Connection connection;
	
    public departmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}

    
	@Override
	public void insert(Department dep) {
		
		 PreparedStatement pStatement = null;
		 
		String sql = "Insert into department (name) values (?) ";
		try {
				 Connection connection = DB.getConnection();
				 pStatement = connection.prepareStatement(sql);
				 pStatement.setString(1, dep.getName());
					 
				 
				 int linhasAfetadas = pStatement.executeUpdate();
				 System.out.println("Inserção bem sucedida! Linhas Afetadas: " + linhasAfetadas);
				 
				 DB.closePreparedStatement(pStatement);
				 DB.closeConnection();
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			finally {
				  DB.closePreparedStatement(pStatement);			
				  DB.closeConnection();
			}
		
		
	}

	@Override
	public void update(Department dep) {
		
		PreparedStatement pStatement=null;
		
		String sql = "UPDATE department SET name = ? where id = ?";
		
		try { Connection connection = DB.getConnection();
				
				pStatement = connection.prepareStatement(sql);
				pStatement.setString(1, dep.getName());
				pStatement.setInt(2, dep.getId());
				int linhasAfetadas = pStatement.executeUpdate();
		        System.out.println("Atualização concluída! Linhas afetadas: " + linhasAfetadas);
		       
		        DB.closePreparedStatement(pStatement);
				DB.closeConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			  DB.closePreparedStatement(pStatement);
			  DB.closeConnection();
		}
	}

	@Override
	public void deletById(Integer id) {
		
		PreparedStatement pStatement = null; 
		
		String sql = "DELETE FROM department  where id = ?";
		
		try { Connection connection = DB.getConnection();
			 pStatement = connection.prepareStatement(sql);
			 pStatement.setInt(1, id);
		
		int linhasAfetadas = pStatement.executeUpdate();
        System.out.println("Exclusão concluída! Linhas afetadas: " + linhasAfetadas);
        
        DB.closePreparedStatement(pStatement);
		DB.closeConnection();
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			  DB.closePreparedStatement(pStatement);
			  DB.closeConnection();
			  }
	}

	@Override
	public Department findById(Integer id) {
		 connection = null;
		 PreparedStatement pStatement =null;
		 ResultSet resultSet = null;
		Department department = null;
		String sql = ("select * from department where id = ?  ");
		try {
			 connection = DB.getConnection();
			 pStatement = connection.prepareStatement(sql);
			 pStatement.setInt(1, id);
			 resultSet = pStatement.executeQuery();
			 
			 if(resultSet.next()) {
			 department = new Department(
			 resultSet.getInt("id"), resultSet.getString("name"));
			 
			  return department;
			 }
			
		}catch (SQLException e) {
		    e.printStackTrace();
		}
		
		finally {
			  DB.closePreparedStatement(pStatement);
			  DB.closeResultSet(resultSet);
			  DB.closeConnection();
			
		}
		 return null;
	}

	@Override
	public List<Department> findAll() {
		 PreparedStatement pStatement = null;
		 ResultSet resultSet = null;
		
		 List<Department> list = new ArrayList<>();
		 String sql = "select * from department " ;
			
		 try {
				 Connection connection = DB.getConnection();
				 
				  pStatement = connection.prepareStatement(sql);
				  resultSet = pStatement.executeQuery();
				 
				 while (resultSet.next()) {
					 Department department = new Department(
							 resultSet.getInt("id"), resultSet.getString("name"));
					 
					list.add(department);
					 
				 }
				
			}catch (SQLException e) {
			    e.printStackTrace();
			
			} finally {
				 DB.closePreparedStatement(pStatement);
				 DB.closeResultSet(resultSet);
				 DB.closeConnection();
			}
		return list;
		 	
	}
	
	
}
