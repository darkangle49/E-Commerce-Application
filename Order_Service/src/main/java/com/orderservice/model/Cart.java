package com.orderservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="cart")
public class Cart {
	
	@Id
	private String id;
	private String username;
	private String email;
	private String status;
	private OrderProduct product;
	public OrderProduct getProduct() {
		return product;
	}
	public void setProduct(OrderProduct product) {
		this.product = product;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Cart(String username, String email, String status,OrderProduct product) {
		super();
		this.username = username;
		this.email = email;
		this.status = status;
		this.product=product;
	}
	public Cart() {
		super();
	}
	

}
