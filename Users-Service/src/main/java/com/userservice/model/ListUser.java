package com.userservice.model;

import java.util.ArrayList;
import java.util.List;

public class ListUser {
	private List<UserRes> users;
	
	public ListUser() {
		users=new ArrayList<>();
	}

	public ListUser(List<UserRes> users) {
		super();
		this.users = users;
	}

	public List<UserRes> getUsers() {
		return users;
	}

	public void setUsers(List<UserRes> users) {
		this.users = users;
	}
	
	

}
