package com.bojan.teamvote.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bojan.teamvote.dao.CanvasjsChartDao;
import com.bojan.teamvote.dao.QuestionDao;
import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.dto.AddVoteDto;
import com.bojan.teamvote.model.enums.QuestionState;
import com.bojan.teamvote.model.exceptions.AlreadyVotedException;
import com.bojan.teamvote.model.exceptions.MustBeLoggedInException;
import com.bojan.teamvote.model.exceptions.QuestionArchivedException;
import com.bojan.teamvote.service.CanvasjsChartService;
import com.bojan.teamvote.service.QuestionService;
import com.bojan.teamvote.service.UserService;
import com.bojan.teamvote.service.VoteService;
import java.util.*;

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
	public String getShowAll(Principal principal, Model model) {

		User user = userService.findByEmail(principal.getName());
		List<Question> voteQuestions = user.getVoteQuestions();
		System.out.println(voteQuestions);
		model.addAttribute("questions", voteQuestions);

		return "views/vote/showAll";
	}

	@GetMapping("/question/{id}")
	public String getVoteQuestion(@PathVariable(name = "id") int questionId, Principal principal, Model model) throws QuestionArchivedException {
		Question question = questionService.findById(questionId);
		System.out.println("Looking at a question");

		// Check to see if question is archived
		if (question.getState() == QuestionState.ARCHIVED) {
			throw new QuestionArchivedException();
		}

		if (questionService.checkIfUserVoted(principal.getName(), question)) {
			System.out.println("User voted");
			return "redirect:/vote/showResults/" + questionId;
		}
		System.out.println("User didnt vote");

		AddVoteDto request = new AddVoteDto();
		model.addAttribute("request", request);

		model.addAttribute("question", question);

		return "views/vote/voteQuestion";
	}

	@PostMapping("/question/{id}")
	public String postVoteQuestion(@PathVariable(name = "id") int questionId, @ModelAttribute(name = "request") AddVoteDto request, @Autowired Principal principal, Model model) throws AlreadyVotedException, QuestionArchivedException {

		request.setUserEmail(principal.getName());
		request.setQuestionId(questionId);

		voteService.addVote(request, principal.getName());

		return "redirect:/profile";
	}

	@GetMapping("/view/{id}")
	public String getViewQuestion(@PathVariable(name = "id") int questionId, @Autowired(required = false) Principal principal, Model model) throws MustBeLoggedInException {

		Question question = questionService.findById(questionId);

		model.addAttribute("question", question);
		return "views/vote/viewQuestion";
	}

	@Autowired
	private CanvasjsChartService canvasjsChartService;

	/**
	 * Shows voting result for the question. If question is public than skip
	 * validation
	 * 
	 * @param model
	 * @param questionId
	 * @param principal
	 * @return
	 * @throws MustBeLoggedInException
	 */
	@GetMapping("/showResults/{id}")
	public String springMVC(Model model, @PathVariable("id") int questionId, @Autowired(required = false) Principal principal) throws MustBeLoggedInException {
		Question question = questionService.findById(questionId);

		List<List<Map<Object, Object>>> canvasjsDataList = canvasjsChartService.getCanvasjsChartData(question);
		System.out.println(canvasjsDataList);
		model.addAttribute("dataPointsList", canvasjsDataList);
		model.addAttribute("question", question);
		return "views/vote/showResults";
	}
	


}
