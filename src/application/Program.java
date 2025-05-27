package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		DateTimeFormatter fdate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse("25/06/1995", fdate); 
		
		Seller seller = new Seller(8, "heloisa ariel", "Helo.2024@gmail.com", date, 2.599,new Department(2,"Electronics") ); 
				
				//new Seller("Antonio ", "toin@gmail.xom", date, 3.500, new Department(2,"Electronics"),7);
		
		//sellerDao.update(seller);
		
		System.out.println(sellerDao.findById(8));
		//System.out.println(sellerDao2.findAll());
		//System.out.println(seller);
	}
	

}
