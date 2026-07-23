package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection connection;

	public DepartmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Department department) {
		
		PreparedStatement preparedStatement = null;
		try {
			String sqlInsert = "Insert into department(Name)values(?) ";
			preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setString(1,department.getName());
			int rowsaAffected = preparedStatement.executeUpdate();
			
			if(rowsaAffected > 0) {
				System.out.println("Insert Success..");
			}else {
				throw new DbException("Not row affected.");
			}
			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public void update(Department department) {
		
		PreparedStatement preparedStatement = null;
		try {
			String sqlUpdate = "update seller set Name = ?,where id = ?";
			
			preparedStatement = connection.prepareStatement(sqlUpdate);
			preparedStatement.setString(1,department.getName());
			preparedStatement.setInt(2, department.getId());
			
			int rowsaAffected = preparedStatement.executeUpdate();
			
			if(rowsaAffected > 0) {
				System.out.println("Update Success..");
			}else {
				throw new DbException("Not row affected.");
			}
			
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
