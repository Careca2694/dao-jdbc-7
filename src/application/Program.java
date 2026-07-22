package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== Test 1: Seller findById ====");
		
		Seller seller = sellerDao.findById(3);

		System.out.print(seller);
		
		System.out.println("\n\n=== Test 2: Seller findByDepartmentId ====");
		
		Department department = new Department(2,null);
		
		List<Seller> list = sellerDao.findByDepartment(department);
		
		for(Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n\n=== Test 3: Seller findAll ====");
		
		list = sellerDao.findAll();
		
		for(Seller obj : list) {
			System.out.println(obj);
		}
		
//		System.out.println("\n\n=== Test 4: Seller insert ====");
//		Seller newSeller = new Seller(null, "Gregorio","gregorio.gmail.com",new Date(),4500.50,department);
//		sellerDao.insert(newSeller);
//		System.out.println("insert new id: " + newSeller.getId());
		
		System.out.println("\n\n=== Test 5: Seller Update ====");
		
		seller = sellerDao.findById(1);
		seller.setName("Maria Amelia");
		seller.setEmail("Maria.gmail.com");
		sellerDao.update(seller);
		System.out.println("Update completed.");

		System.out.println("\n\n=== Test 6: Seller Delete ====");
		sellerDao.deleteById(9);
		
		System.out.println("Delete completed");
	}

}
