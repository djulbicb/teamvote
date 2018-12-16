package com.bojan.teamvote.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bojan.teamvote.model.dto.AddVoteDto;
import com.bojan.teamvote.model.exceptions.AlreadyVotedException;
import com.bojan.teamvote.model.exceptions.QuestionArchivedException;
import com.bojan.teamvote.model.exceptions.UserNotFound;
import com.bojan.teamvote.service.VoteService;

@RestController
@RequestMapping("email")
public class EmailVoteController {
	
	@Autowired
	VoteService voteService;
	
	@RequestMapping("vote/{email}/{questionId}/{id}")
	public void vote(
			@PathVariable("email") String email,
			@PathVariable("id") int id,
			@PathVariable("questionId") int questionId
			) throws AlreadyVotedException, QuestionArchivedException, UserNotFound {
		
		AddVoteDto vote = new AddVoteDto();
		vote.setQuestionId(questionId);
		vote.setUserEmail(email);
		vote.setSelVotes(new String[] {id+""});
		
		voteService.addVote(vote, email);
		
		System.out.println(email);
		System.out.println(id);
		System.out.println(questionId);
		System.out.println("Voted");
	}

}
