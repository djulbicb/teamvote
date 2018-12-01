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
	
	public void addVote(AddVoteDto request) {
		
		Question question = questionDao.findById(request.getQuestionId()).get();
		User user = userDao.findByEmail(request.getUserEmail());
		
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
		
		
		
		System.out.println("Vote added " + vote);
		voteDao.save(vote);
	}

}
