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
public class RoleService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;

	public void addRole(String name) {
		Role role = new Role();
		role.setName(name);
		roleDao.save(role);
	}
}
