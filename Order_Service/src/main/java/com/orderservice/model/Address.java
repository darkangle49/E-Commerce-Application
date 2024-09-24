package com.orderservice.model;

import javax.validation.constraints.NotNull;




public class Address {
	
	@NotNull(message="Street must not be empty")
	String street;
	@NotNull(message="Pincode must not be empty")
	String pincode;
	@NotNull(message="City must not be empty")
	String city;
	@NotNull(message="State must not be empty")
	String state;
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Address(String street, String pincode, String city, String state) {
		super();
		this.street = street;
		this.pincode = pincode;
		this.city = city;
		this.state = state;
	}
	public Address() {
		super();
	}
	

}
