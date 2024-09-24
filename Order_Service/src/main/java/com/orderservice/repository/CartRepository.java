package com.orderservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderservice.model.Cart;

public interface CartRepository extends MongoRepository<Cart, String> {

	public List<Cart> findByusername(String name);

	public void deleteByid(String id);
	
	public Cart findByid(String id);
	

}
