package com.bojan.teamvote.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bojan.teamvote.model.User;
import com.bojan.teamvote.service.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	UserService userService;

	@GetMapping("")
	public String getProfile(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());

		model.addAttribute("user", user);
		return "views/profile/profile";
	}

}
