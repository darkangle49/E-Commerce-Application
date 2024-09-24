package com.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderservice.model.Order;
import com.orderservice.repository.OrderRepository;


@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;
	
	@Autowired
	private MongoTemplate mongo;
	
	@Transactional
	public List<Order> cart(String name)
	{
		Query que=new Query();
	    que.addCriteria(Criteria.where("username").is(name).and("status").is("pending"));
	    List<Order> order = mongo.find(que, Order.class);
	    
	    	return order;
	}
	
	@Transactional
	public List<Order> findByusername(String name)
	{
		Query que=new Query();
	    que.addCriteria(Criteria.where("username").is(name).and("status").is("success"));
	    List<Order> order = mongo.find(que, Order.class);
	    
	    	return order;
	}
	@Transactional
	public void deleteByidn(String id) {
		repo.deleteOrderByid(id);
	}
	
}
