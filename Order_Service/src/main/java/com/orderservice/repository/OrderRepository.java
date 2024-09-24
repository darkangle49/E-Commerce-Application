package com.orderservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderservice.model.Order;

public interface OrderRepository extends MongoRepository<Order, Long> {

	public List<Order> findBystatus(String name);

	public List<Order> findByusername(String name);

	public void deleteOrderByid(String id);

	

}
