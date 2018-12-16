package com.bojan.teamvote.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bojan.teamvote.dao.OpinionDao;
import com.bojan.teamvote.dao.QuestionDao;
import com.bojan.teamvote.dao.UserDao;
import com.bojan.teamvote.dao.VoteDao;
import com.bojan.teamvote.model.Opinion;
import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.Vote;
import com.bojan.teamvote.model.dto.AddVoteDto;
import com.bojan.teamvote.model.enums.QuestionState;
import com.bojan.teamvote.model.exceptions.AlreadyVotedException;
import com.bojan.teamvote.model.exceptions.QuestionArchivedException;
import com.bojan.teamvote.model.exceptions.UserNotFound;

@Transactional
@Service
public class VoteService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	QuestionDao questionDao;
	
	@Autowired
	VoteDao voteDao;
	
	@Autowired
	OpinionDao opinionDao;
	
	//	CREATE
	////////////////////////////////////////////////////////////////
	
	/**
	 * Registers when user voted on a question. Holds information on user, question and vote objects
	 * @param request - Data transfer object between vote view and view controller
	 * @param principal - Spring security
	 * @throws AlreadyVotedException - throws error if user already vote
	 * @throws QuestionArchivedException - user is trying to vote on a archived question
	 * @throws UserNotFound - User is trying through rest service and using a wrong email
	 */
	public void addVote(AddVoteDto request, String principal) throws AlreadyVotedException, QuestionArchivedException, UserNotFound {
		
		Question question = questionDao.findById(request.getQuestionId()).get();
		
		// Check to see if question is archived
		if (question.getState() == QuestionState.ARCHIVED) {
			throw new QuestionArchivedException();
		}
		User user = userDao.findByEmail(principal);
		if (user == null) {
			throw new UserNotFound();
		}	
		
		if (checkIfUserVoted(user, question)) {
			System.out.println("User voted");
			throw new AlreadyVotedException();
		}
		
		Vote vote = new Vote();
		vote.setUser(user);
		user.getVotes().add(vote);
		
		vote.setQuestion(question);
		question.getVotes().add(vote);
		
		String[] selVotes = request.getSelVotes();
		for (String string : selVotes) {
			try {
				int opinionId = Integer.parseInt(string);
				Opinion opinion = opinionDao.findById(opinionId).get();
				
				opinion.getVotes().add(vote);
				vote.setOpinions(opinion);

			} catch (Exception e) {
				System.out.println("Error saving vote");
			}
		}
		
		question.getVotedVoters().add(user);
		user.getVotedQuestions().add(question);
		user.getVoteQuestions().remove(question);
		question.getVotedVoters().add(user);
		question.getVoters().remove(user);
		
		System.out.println("Vote added " + vote);
		userDao.save(user);
		voteDao.save(vote);
	}

	//	UTILITY
	////////////////////////////////////////////////////////////////
	
	/**
	 * Checks if user has already voted on a specified question
	 * @param user - User
	 * @param q - Question 
	 * @return
	 */
	public boolean checkIfUserVoted(User user, Question q) {
		System.out.println("Vote service - check if voted");
		System.out.println(q.getVotedVoters());
		
		if(q.getVotedVoters().contains(user)) {
			System.out.println("User already voted - service");
			return true;
		};
		
		
		return false;
	}

	
}
