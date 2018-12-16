package com.bojan.teamvote.service;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bojan.teamvote.dao.RoleDao;
import com.bojan.teamvote.dao.SettingDao;
import com.bojan.teamvote.dao.UserDao;
import com.bojan.teamvote.model.Role;
import com.bojan.teamvote.model.Setting;
import com.bojan.teamvote.model.Team;
import com.bojan.teamvote.model.User;

@Service
@Transactional
public class UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	RoleDao roleDao;

	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	SettingDao settingDao;
	
	//	RETRIVE
	////////////////////////////////////////////////////////////////
	
	public User findById(int id) {
		return userDao.findById(id).get();
	}
	
	public List<User> findAll() {
		return userDao.findAll();
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
	
	//	CREATE
	////////////////////////////////////////////////////////////////
	
	public void addUser(User user) {
		
		user.setPassword(encoder.encode(user.getPassword())); 
		
		Role userRole = new Role("USER");
		Role role = roleDao.findById("USER").get();
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);
		
		System.out.println(role);
		
		Setting setting = new Setting();
		setting.setUser(user);
		setting.setAddTeam(true);
		setting.setSendEmail(true);		
		
		user.setSetting(setting);
		
		userDao.save(user);		
	}

	public void addAdmin(User user) {

		user.setPassword(encoder.encode(user.getPassword())); 
		Role userRole = new Role("ADMIN");
		Role role = roleDao.findById("ADMIN").get();
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);
		
		Setting setting = new Setting();
		setting.setUser(user);
		setting.setAddTeam(true);
		setting.setSendEmail(true);
		user.setSetting(setting);
		
		userDao.save(user);
	}
	
	//	UPDATE
	////////////////////////////////////////////////////////////////
	
	public User updateUser(String email, User user) {
		User dbUser = userDao.findByEmail(email);
		dbUser.setFirstName(user.getFirstName());
		dbUser.setLastName(user.getLastName());
		dbUser.setPassword(encoder.encode(user.getPassword()));
		
		dbUser.setName(user.getName());
		if (!user.getAvatarImage().isEmpty() && !user.getAvatar().equals("")) {
			dbUser.setAvatar(user.getAvatar());
		}
		
		return userDao.save(dbUser);
	}
	
	public void updateSetting(@Valid Setting setting, String name) {
		User user = userDao.findByEmail(name);
		Setting dbSetting = user.getSetting();
		
		dbSetting.setAddTeam(setting.getAddTeam());
		dbSetting.setSendEmail(setting.getSendEmail());
		
		userDao.save(user);
	}

	//	DELETE
	////////////////////////////////////////////////////////////////
	
	public void deleteUser(String name) {
		User user = userDao.findByEmail(name);
		userDao.delete(user);
	}
	
	//	UTILITY
	////////////////////////////////////////////////////////////////
	
	public boolean ifExistsByName(String username) {
		return userDao.ifExistsByName(username);
	}
	
	public boolean ifExistsByEmail(String email) {
		return userDao.ifExistsByEmail(email);
	}

	

	
	


}
