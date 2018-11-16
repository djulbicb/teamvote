package com.bojan.teamvote.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.dto.AddQuestionDto;
import com.bojan.teamvote.service.QuestionService;
import com.bojan.teamvote.service.UserService;

@Controller
@RequestMapping("/profile")
public class QuestionController {

	@Autowired
	UserService userService;
	
	@Autowired
	QuestionService questionService;
	
	@GetMapping("showAskedQuestions")
	public String getShowAskedQuestions(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());	
		model.addAttribute("questions", user.getQuestions());
		return "views/profile/showAskedQuestions";
	}
	
	@GetMapping("addQuestion")
	public String getAddQuestion(Model model, Principal principal) {
		AddQuestionDto request = new AddQuestionDto();
		
		model.addAttribute("request", request);
		return "views/profile/addQuestion";
	}

	@PostMapping("addQuestion")
	public String postAddQuestion(@Valid @ModelAttribute("request") AddQuestionDto request, BindingResult results, Model model, Principal principal) {
		if (results.hasErrors()) {
			System.out.println("Not valid");
			model.addAttribute("request", request);
			return "views/profile/addQuestion";
		}
		
		User user = userService.findByEmail(principal.getName());
		System.out.println(user);
		System.out.println(request);
		
		questionService.addQuestion(request, user);
		System.out.println("Question saved");
		return "redirect:/profile?addQuestion";
	}
}