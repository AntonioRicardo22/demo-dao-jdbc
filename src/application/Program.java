package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.dao.impl.sellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("====== TEST 1 : SELLER BYID ======");
		System.out.println();
		Seller seller = sellerDao.findById(4);
		System.out.println(seller);
		
		System.out.println("====== TEST 2 : SELLER BY DEPARTMENT ======");
		System.out.println();
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		 for (Seller objSeller : list) {
			 System.out.println(objSeller);
		 }	 
			 
		System.out.println("====== TEST 3 : SELLER findAll ======");	 
		System.out.println();
		 List<Seller> listall = sellerDao.findAll();
		 for (Seller seller2 : listall) {
			 System.out.println(seller2);
			 
		 }
		
	}
	

}
