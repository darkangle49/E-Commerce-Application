package com.orderservice.model;

import java.math.BigDecimal;

public class OrderProduct {
	
	private String name;
	
	private String info;
	
	private BigDecimal price;
	private String category;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public OrderProduct( String name, String info, BigDecimal price, String category) {
		super();
		
		this.name = name;
		this.info = info;
		this.price = price;
		this.category = category;
	}
	public OrderProduct() {
		super();
	}

}
