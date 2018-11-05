package com.bojan.teamvote;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bojan.teamvote.dao.RoleDao;
import com.bojan.teamvote.dao.UserDao;
import com.bojan.teamvote.model.Role;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamvoteApplicationTests {

	@Autowired
	UserService userService;


	@Test
	public void contextLoads() {
	}

	@Test
	public void initDb() {
		{
			User user = new User();
			user.setName("admin");
			user.setPassword("1234");
			user.setEmail("admin@gmail.com");
			userService.createAdmin(user);
		}

		{
			User user = new User();
			user.setName("user");
			user.setPassword("1234");
			user.setEmail("user@gmail.com");
			userService.createUser(user);
		}
	}

}
