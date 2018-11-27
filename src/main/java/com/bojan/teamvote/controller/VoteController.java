package com.bojan.teamvote.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.dto.AddVoteDto;
import com.bojan.teamvote.service.QuestionService;
import com.bojan.teamvote.service.UserService;
import com.bojan.teamvote.service.VoteService;

@Controller
@RequestMapping("/vote")
public class VoteController {

	@Autowired
	UserService userService;
		
	@Autowired
	QuestionService questionService;

	@Autowired
	VoteService voteService;
	
	@GetMapping("showAll")
	public String getShowAll(
			Principal principal,
			Model model) {
		
		User user = userService.findByEmail(principal.getName());
		List<Question> voteQuestions = user.getVoteQuestions();
		System.out.println(voteQuestions);
		model.addAttribute("questions", voteQuestions);
		
		return "views/vote/showAll";
	}
	
	@GetMapping("/question/{id}")
	public String getVoteQuestion(
			@PathVariable(name="id") int questionId,
			Principal principal,
			Model model
			) {
		AddVoteDto request= new AddVoteDto();
		model.addAttribute("request", request);
		
		Question question = questionService.findById(questionId);
		model.addAttribute("question", question);
		
		return "views/vote/voteQuestion";
	}
	
	@PostMapping("/question/{id}")
	public String postVoteQuestion(
			@PathVariable(name="id") int questionId,
			@ModelAttribute(name="request") AddVoteDto request,
			Principal principal,
			Model model
			) {

		System.out.println(request);
		
		request.setUserEmail(principal.getName());
		request.setQuestionId(questionId);
		
		voteService.addVote(request);
		
		return "redirect:/profile";
	}
	
}
