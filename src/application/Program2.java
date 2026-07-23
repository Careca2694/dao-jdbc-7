package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		Department department = new Department(null,"Logistics");
		
		System.out.println("\n\n=== Test 5: Seller Insert ====");
		
		departmentDao.insert(department);
		
		

	}

}
