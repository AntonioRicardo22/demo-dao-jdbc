package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.dao.DepartmentDao;
import model.entities.Department;


public class departmentDaoJDBC implements DepartmentDao{
	
	
	private Connection connection;
	
    public departmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}

    
	@Override
	public void insert(Department dep) {
		
		 
		String sql = "Insert into department (name) values (?) ";
		try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
				 pStatement.setString(1, dep.getName());
					 
				 
				 int linhasAfetadas = pStatement.executeUpdate();
				 System.out.println("Inserção bem sucedida! Linhas Afetadas: " + linhasAfetadas);
				
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}

	@Override
	public void update(Department dep) {
	
		String sql = "UPDATE department SET name = ? where id = ?";
		
		try ( PreparedStatement pStatement = connection.prepareStatement(sql)){ 
				pStatement.setString(1, dep.getName());
				pStatement.setInt(2, dep.getId());
				int linhasAfetadas = pStatement.executeUpdate();
		        System.out.println("Atualização concluída! Linhas afetadas: " + linhasAfetadas);
		       
		       
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletById(Integer id) {
		
		String sql = "DELETE FROM department  where id = ?";
		
		try (PreparedStatement pStatement = connection.prepareStatement(sql)){ 
			 pStatement.setInt(1, id);
		
		int linhasAfetadas = pStatement.executeUpdate();
        System.out.println("Exclusão concluída! Linhas afetadas: " + linhasAfetadas);
       
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Department findById(Integer id) {
		

		String sql = ("select * from department where id = ?  ");
		try ( PreparedStatement pStatement  = connection.prepareStatement(sql)){
			 pStatement.setInt(1, id);
			
			 try (ResultSet resultSet = pStatement.executeQuery()) { 
		            if (resultSet.next()) {
		                return new Department(resultSet.getInt("id"), resultSet.getString("name"));
		            }
		        }
			
		}catch (SQLException e) {
		    e.printStackTrace();
		}
		
		 return null;
	}

	@Override
	public List<Department> findAll() {
		
		List<Department> list = new ArrayList<>();
		 String sql = "select * from department " ;
			
		 try (PreparedStatement  pStatement = connection.prepareStatement(sql); ResultSet resultSet = pStatement.executeQuery()){
				 
				 while (resultSet.next()) {
					 Department department = new Department(
							 resultSet.getInt("id"), resultSet.getString("name"));
					 
					list.add(department);
					 
				 }
				
			}catch (SQLException e) {
			    e.printStackTrace();
			
			}
		return list;
		 	
	}
	
	
}
