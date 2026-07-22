package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDao {

	public void insert(Seller seller);

	public void update(Seller seller);

	public void deleteById(Integer id);

	Seller findById(Integer id);

	List<Seller> findAll();

}
