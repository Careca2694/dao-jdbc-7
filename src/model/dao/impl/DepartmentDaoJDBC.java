package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
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
			String sqlUpdate = "update department set Name = ? where id = ?";
			
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
		
        PreparedStatement preparedStatement = null;
		
		try {
			String sqlDelete = "DELETE from department where id = ?";
			preparedStatement = connection.prepareStatement(sqlDelete);
			
			preparedStatement.setInt(1, id);
			
		   preparedStatement.executeUpdate();
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(preparedStatement);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sqlFindById =  "Select department.*, department.Name as DepName from department where department.Id = ?";
			preparedStatement = connection.prepareStatement(sqlFindById);
			preparedStatement.setInt(1, id);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				
				Department dep = instantiateDepartment(resultSet);
				
				return dep;
			}
			return null;
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(resultSet);
		}
			
			
		}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		
		Department department = new Department();
		
		department.setId(resultSet.getInt("Id"));
		department.setName(resultSet.getString("Name"));
		
		return department;
		
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
