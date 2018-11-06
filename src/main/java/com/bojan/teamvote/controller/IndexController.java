package com.bojan.teamvote.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bojan.teamvote.model.User;
import com.bojan.teamvote.service.UserService;

@Controller
public class IndexController {

	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String getIndex() {
		return "views/index";
	}
	
	@GetMapping("/login")
	public String getLogin(
			Model model,
			@RequestParam(name="register", required=false) String register
			) {
		if (register!= null) {
			System.out.println("Hello");
			model.addAttribute("msg", "Registration was succesfull");
		}
		return "views/login";
	}
	
	@GetMapping("/register")
	public String getRegister(
			Model model) {
		User user = new User();
		
		
		
		model.addAttribute("user", user);
		return "views/register";
	}
	
	@PostMapping("/register")
	public String postRegister(@Valid @ModelAttribute(name="user") User user) {	
		userService.addUser(user);
		return "redirect:/login?register";
	}
}
