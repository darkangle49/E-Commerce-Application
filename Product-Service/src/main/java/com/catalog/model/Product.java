package com.catalog.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;





@Document(collection = "Product")
public class Product {

	@Id
	private String id;
	private String name;
	
	private String info;
	
	private BigDecimal price;
	private String category;
	
	public Product() {
		
	}

	public Product( String name, String info, BigDecimal price, String category) {
		super();
		
		this.name = name;
		this.info = info;
		this.price = price;
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	
	public String getInfo() {
		return info;
	}

	
	public void setInfo(String info) {
		this.info = info;
	}

	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name  + ", info=" + info + ", price=" + price
				+ "]";
	}
	
	
}