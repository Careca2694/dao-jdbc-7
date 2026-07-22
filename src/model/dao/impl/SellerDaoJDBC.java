package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	
	private Connection connection;
	
	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Seller seller) {
		
		PreparedStatement preparedStatement = null;
		
		try {
			String sqlInsert = "insert into seller(Name, Email, BirthDate, BaseSalary, DepartmentId)values(?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, seller.getName());
			preparedStatement.setString(2, seller.getEmail());
			preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			preparedStatement.setDouble(4, seller.getBaseSalary());
			preparedStatement.setInt(5, seller.getDepartment().getId());
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			if(rowsAffected > 0) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				if(resultSet.next()) {
					int id = resultSet.getInt(1);
					seller.setId(id);
				}
				
				DB.closeResultSet(resultSet);
			}else {
				throw new DbException("Unexpected error, No rows affected");
			}
			
		}catch(SQLException e) {

		}finally {
			DB.closeStatement(preparedStatement);
		}
		
	}

	@Override
	public void update(Seller seller) {
		
        PreparedStatement preparedStatement = null;
		
		try {
			String sqlUpdate = "update seller set Name = ?, Email = ?, BirthDate = ?, BaseSalary = ? , DepartmentId = ? where id = ?";
			preparedStatement = connection.prepareStatement(sqlUpdate);
			
			preparedStatement.setString(1, seller.getName());
			preparedStatement.setString(2, seller.getEmail());
			preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			preparedStatement.setDouble(4, seller.getBaseSalary());
			preparedStatement.setInt(5, seller.getDepartment().getId());
			preparedStatement.setInt(6, seller.getId());
			
			preparedStatement.executeUpdate();
			
		}catch(SQLException e) {

		}finally {
			DB.closeStatement(preparedStatement);
		}
	}

	@Override
	public void deleteById(Integer id) {
		
		PreparedStatement preparedStatement = null;
		
		try {
			String sqlDelete = "DELETE from seller where id = ?";
			preparedStatement = connection.prepareStatement(sqlDelete);
			
			preparedStatement.setInt(1, id);
			
			int rows = preparedStatement.executeUpdate();
			
			if(rows > 0) {
				System.out.print(rows);
			}else {
				throw new DbException("Fall not rows affeted");
			}
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(preparedStatement);
		}
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sqlFindById = "Select seller.*, department.Name as DepName from seller inner join department on seller.DepartmentId = department.Id where seller.Id = ?";
			preparedStatement = connection.prepareStatement(sqlFindById);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				Department dep = instantiateDepartment(resultSet);
				
				Seller seller = instantiateSeller(resultSet, dep);
				
				return seller;
			}
			return null;
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(resultSet);
		}
		
	}

	private Seller instantiateSeller(ResultSet resultSet, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(resultSet.getInt("Id"));
		seller.setName(resultSet.getString("Name"));
		seller.setEmail(resultSet.getString("Email"));
		seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
		seller.setBirthDate(resultSet.getDate("BirthDate"));
		seller.setDepartment(dep);
		return seller;
	}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		Department dep = new Department();
		dep.setId(resultSet.getInt("DepartmentId"));
		dep.setName(resultSet.getString("Name"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sqlFindAll = "SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department"
					+ " ON seller.DepartmentId = department.Id ORDER BY Name";
			
			preparedStatement = connection.prepareStatement(sqlFindAll);
			
			resultSet = preparedStatement.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer,Department> map = new HashMap<>();
			
			while(resultSet.next()) {
				
				Department dep = map.get(resultSet.getInt("DepartmentId"));
				
				if(dep == null) {
					
					dep = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(resultSet, dep);
				
				list.add(seller);
				
			}
			return list;
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(resultSet);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String sqlFindByDepartment = "Select seller.*, department.Name as DepName from seller inner join department on seller.DepartmentId = department.Id where DepartmentId = ? order by Name";
			preparedStatement = connection.prepareStatement(sqlFindByDepartment);
			preparedStatement.setInt(1, department.getId());
			resultSet = preparedStatement.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer,Department> map = new HashMap<>();
			
			while(resultSet.next()) {
				
				Department dep = map.get(resultSet.getInt("DepartmentId"));
				
				if(dep == null) {
					
					dep = instantiateDepartment(resultSet);
					map.put(resultSet.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantiateSeller(resultSet, dep);
				
				list.add(seller);
				
			}
			return list;
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeStatement(preparedStatement);
			DB.closeResultSet(resultSet);
		}
		
	}

}
