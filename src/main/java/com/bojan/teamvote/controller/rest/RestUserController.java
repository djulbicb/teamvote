package com.bojan.teamvote.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bojan.teamvote.model.*;
import com.bojan.teamvote.service.UserService;

@RestController
@RequestMapping("/rest")
public class RestUserController {

	@Autowired
	UserService userService;
	
	@PostMapping("findUsersByFilters")
	public List<User> findUsersByFilters(
			@RequestParam(name="email", defaultValue="") String email, 
			@RequestParam(name="firstName", defaultValue="") String firstName, 
			@RequestParam(name="lastName", defaultValue="") String lastName)
	{
		System.out.println(email);
		System.out.println(firstName);
		System.out.println(lastName);
		List<User> findUsers = userService.findUsersByFilters(email, firstName, lastName);
		//List<User> findUsers = userService.findUsers(firstName);
		System.out.println("--------");
		System.out.println(findUsers);
		return findUsers;
	}
	
}
