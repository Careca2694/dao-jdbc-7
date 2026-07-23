package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		//Department department = new Department(null,"Logistics");
//		
//		System.out.println("\n\n=== Test 1: Seller Insert ====");
//		
//		departmentDao.insert(department);

		System.out.println("\n\n=== Test 2: Seller FindById ====");
		
	    Department newDepartment = departmentDao.findById(5);
	    
	    System.out.println(newDepartment);
	    
	    
	    System.out.println("\n\n=== Test 3: Seller Update ====");
	    
	     Department department = departmentDao.findById(5);
	    department.setName("Manager");
	    
	    departmentDao.update(department);
	    
	    System.out.println("Update completed." + department);


		
		

	}

}
