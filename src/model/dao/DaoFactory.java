package model.dao;

import model.dao.impl.sellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new sellerDaoJDBC();
	}
}
