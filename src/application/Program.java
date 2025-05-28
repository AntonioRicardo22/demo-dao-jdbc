package application;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		LocalDate birthDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
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
		 System.out.println("====== TEST 4 : SELLER insert ======");	 
		 
		Seller newSeller = new Seller(null, "greg", "greg@gmail.com", birthDate,4000.00,department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! new id = "+ newSeller.getId());
		
		
		 System.out.println("====== TEST 5 : SELLER UPDATE ======");
		 seller =  sellerDao.findById(1);
		 seller.setName("marta Waine");
		 sellerDao.update(seller);
		 
	}
	

}
