package com.bojan.teamvote.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bojan.teamvote.dao.RoleDao;
import com.bojan.teamvote.dao.UserDao;
import com.bojan.teamvote.model.Role;
import com.bojan.teamvote.model.User;

@Service
@Transactional
public class UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;

	public void createUser(User user) {
		BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword())); 
		Role userRole = new Role("USER");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		userDao.save(user);		
	}

	public void createAdmin(User user) {
		BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword())); 
		Role userRole = new Role("ADMIN");
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		user.setRoles(roles);
		userDao.save(user);
	}
}
