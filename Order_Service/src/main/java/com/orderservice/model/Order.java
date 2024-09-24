package com.orderservice.model;

import java.time.LocalDate;


import javax.validation.Valid;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Document(collection="orders")

public class Order {

    @Id
    
    private String id;
    private String username;
    private String email;
    

	@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated;

    private String status;

    @JsonManagedReference
    
    @Valid
     private OrderProduct orderProducts ;
    

	private Address address;
	

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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OrderProduct getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(OrderProduct p) {
		this.orderProducts = p;
	}

	public Order(LocalDate dateCreated, String status, @Valid OrderProduct orderProducts,Address address) {
		super();
		
		
		this.dateCreated = dateCreated;
		this.status = status;
		this.orderProducts = orderProducts;
		
		this.address=address;
		
	}

	public Order() {
		super();
	}
    

    
    
    
}