package application;


import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;


public class Program2 {

	public static void main(String[] args) {

		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
	
		
		System.out.println("===== test 1  - DEPARTMENT FINDBYID ======= ");
		Department department = departmentDao.findById(2);
		System.out.println(department);
		System.out.println();
		System.out.println("===== test 2  - DEPARTMENT FINDALL ======= ");
		List<Department> departments = departmentDao.findAll();
		for (Department dep : departments) {
			System.out.println(dep);
		}
		System.out.println();
		
		System.out.println("===== test 3  - DEPARTMENT DELETBYID ======= ");
		departmentDao.deletById(18);
		
		System.out.println();
		
		System.out.println("===== test 4  - DEPARTMENT UPDATE ======= ");
		Department department2 = departmentDao.findById(2);
		department2.setName("Food");
		departmentDao.update(department2);
	
		System.out.println("===== test   - DEPARTMENT INSERT ======= ");
		Department department3 = new Department(null,"Magazine");
		departmentDao.insert(department3);
		
	}
	

}
