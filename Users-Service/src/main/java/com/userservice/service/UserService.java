package com.userservice.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import com.userservice.model.User;
import com.userservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository rep;
	
	
	@Transactional
	public User addUser(User user)
	{
		User u1=new User(user.getUsername(),user.getEmail());
		return rep.save(u1);
	}
	@Transactional
	public User findByUsername(String username) {
		return rep.findByUsername(username);
	}

	@Transactional
	public List<User> findAll() {
		List<User> user1=rep.findAll();
		return user1;
	}
	
	

}
