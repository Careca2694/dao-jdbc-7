package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	public void insert(Department department);
	public void update(Department department);
	public void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll ();
	
	
}
