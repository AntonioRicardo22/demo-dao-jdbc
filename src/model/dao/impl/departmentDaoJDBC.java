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

	@Override
	public void insert(Department dep) {
		
		String sql = "Insert into departamento (name) values (?) ";
		try {
				 Connection connection = DB.getConnection();
				 PreparedStatement pStatement = connection.prepareStatement(sql);
				 pStatement.setString(1, dep.getName());
					 
				 
				 int linhasAfetadas = pStatement.executeUpdate();
				 System.out.println("Inserção bem sucedida! Linhas Afetadas: " + linhasAfetadas);
				 
				 DB.closePreparedStatement(pStatement);
				 DB.closeConnection();
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		
	}

	@Override
	public void update(Department dep) {
		String sql = "UPDATE department SET name = ? where id = ?";
		
		try { Connection connection = DB.getConnection();
				
				PreparedStatement pStatement = connection.prepareStatement(sql);
				pStatement.setString(1, dep.getName());
				
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
		String sql = "DELETE FROM department  where id = ?";
		
		try { Connection connection = DB.getConnection();
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
	public Department findById(Integer id) {
		Department department = null;
		String sql = ("select * from department where id = ?  ");
		try {
			 Connection connection = DB.getConnection();
			 PreparedStatement pStatement = connection.prepareStatement(sql);
			 pStatement.setInt(1, id);
			 ResultSet resultSet = pStatement.executeQuery();
			 
			 department = new Department(
			resultSet.getInt("departmentid"), resultSet.getString("name"));
			 
			  DB.closePreparedStatement(pStatement);
			  DB.closeResultSet(resultSet);
			  DB.closeConnection();
			
		}catch (SQLException e) {
		    e.printStackTrace();
		}
		
		return department ;
		
	}

	@Override
	public List<Department> findAll() {
		 List<Department> list = new ArrayList<>();
		 String sql = "select * from department " ;
			
		 try {
				 Connection connection = DB.getConnection();
				 
				 PreparedStatement pStatement = connection.prepareStatement(sql);
				 ResultSet resultSet = pStatement.executeQuery();
				 
				 while (resultSet.next()) {
					 Department department = new Department(
							 resultSet.getInt("departmentid"), resultSet.getString("name"));
					 
					list.add(department);
					 
				 }
				 
				 DB.closePreparedStatement(pStatement);
				 DB.closeResultSet(resultSet);
				 DB.closeConnection();
				
			}catch (SQLException e) {
			    e.printStackTrace();
			}
			
		 return list;
	}
	
}
