package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		Department department = new Department(null,"Logistics");
		
    	System.out.println("\n\n=== Test 1: Department Insert ====");
//		
//		departmentDao.insert(department);

		System.out.println("\n\n=== Test 2: Department FindById ====");
		
	    Department newDepartment = departmentDao.findById(5);
	    
	    System.out.println(newDepartment);
	    
	    
	    System.out.println("\n\n=== Test 3: Department Update ====");
	    
	     department = departmentDao.findById(5);
	    department.setName("Manager");
	    
	    departmentDao.update(department);
	    
	    System.out.println("\n\n=== Test 4: Department Delete ====");
	    
	    departmentDao.deleteById(12);
	    	    
	    System.out.println("Deleted success.");
	    
	    
	    System.out.println("\n\n=== Test 4: Department findAll  ====");
	    
	    List<Department> allDepartment = departmentDao.findAll();
	    
	    for(Department dep : allDepartment) {
	    	 System.out.println("Department " + dep);
	    }
		
		

	}

}
