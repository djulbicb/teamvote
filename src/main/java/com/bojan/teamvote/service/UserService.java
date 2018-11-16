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

	
	public User findById(int id) {
		return userDao.findById(id).get();
	}
	
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	public void addUser(User user) {
		BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword())); 
		
		Role userRole = new Role("USER");
		Role role = roleDao.findById("USER").get();
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);
		userDao.save(user);		
	}

	public void addAdmin(User user) {
		BCryptPasswordEncoder  encoder = new  BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword())); 
		Role userRole = new Role("ADMIN");
		Role role = roleDao.findById("ADMIN").get();
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);
		userDao.save(user);
	}
	
	public User updateUser(User user) {
		return userDao.save(user);
	}
	
	public boolean ifExistsByName(String username) {
		return userDao.ifExistsByName(username);
	}
	
	public boolean ifExistsByEmail(String email) {
		return userDao.ifExistsByEmail(email);
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
		
	}

	public List<User> findUsers(String firstName){
		return userDao.findByFirstNameLike(firstName);
	}
	
	public List<User> findUsersByFilters(String email, String firstName, String lastName) {
		List<User> users = userDao.findUsersByFilters(email, firstName, lastName);
		System.out.println(users);
		return users;
	}
}
