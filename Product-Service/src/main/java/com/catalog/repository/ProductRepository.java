package com.catalog.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.catalog.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

	Product findByname(String productId);
	@Query(value="{'category ':?0}",fields="{'category ':1}" )
	List<Product> findBycategory(String category);
	void deleteByName(String name);
	 

}
