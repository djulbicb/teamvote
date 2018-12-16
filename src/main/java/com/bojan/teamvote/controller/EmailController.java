package com.bojan.teamvote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.service.QuestionService;
import com.bojan.teamvote.util.EmailUtil;

@Deprecated
@Controller
@RequestMapping("email")
/**
 * Used in testing email sending functionality
 * @author Bojan
 *
 */
public class EmailController {

	@Autowired
	EmailUtil emailUtil;
	
	@Autowired
	QuestionService questionService;
	
	// UTILITY
	////////////////////////////////////////////////////////////////
	
	/*
	@GetMapping("send")
	public String sendEmail() {
		//body.append("<form action='http://localhost:8080/email/vote' method='POST'>");
		String email = "djulbic.bojan@gmail.com";
		Question q = questionService.findById(2);
		System.out.println(q);
		String body = emailUtil.wrapQuestionIntoTable(q, email);
		System.out.println(body);
		emailUtil.sendEmail("djulbic.bojan@gmail.com", "New question", body);
		return "redirect:/";
	}
	*/

	

	
}
