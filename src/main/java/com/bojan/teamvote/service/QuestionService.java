package com.bojan.teamvote.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bojan.teamvote.dao.QuestionDao;
import com.bojan.teamvote.dao.TeamDao;
import com.bojan.teamvote.dao.UserDao;
import com.bojan.teamvote.model.Opinion;
import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.model.Team;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.dto.AddQuestionDto;
import com.bojan.teamvote.model.enums.QuestionState;
import com.bojan.teamvote.util.EmailUtil;

@Service
@Transactional
public class QuestionService {

	@Autowired
	QuestionDao questionDao;

	@Autowired
	UserDao userDao;

	@Autowired
	TeamDao teamDao;

	@Autowired
	EmailUtil emailUtil;
	
	//	RETRIVE
	////////////////////////////////////////////////////////////////
	
	/**
	 * Returns question based on the question id
	 * @param questionId
	 * @return
	 */
	public Question findById(int questionId) {
		Question question = questionDao.findById(questionId).get();
		return question;
	}
	
	/**
	 * Returns filtered question that are current active
	 * @return
	 */
	public List<Question> findAllPublicQuestions(){
		return questionDao.findAllQuestionsByState(QuestionState.ACTIVE);
	}
	
	//	CREATE
	////////////////////////////////////////////////////////////////
	
	/**
	 * Creates a private question for each selected team in the addQuestion.html form.
	 * 
	 * @param request - form data received from addQuestion.html
	 * @param user - principal user
	 */
	public void addQuestion(@Valid AddQuestionDto request, User user) {
		Question question = new Question();
		List<Opinion> opinions = new ArrayList<>();
		question.setOpinions(opinions);

		question.setText(request.getText());

		for (String option : request.getOptionParams()) {
			Opinion op = new Opinion();
			op.setQuestion(question);
			op.setText(option);
			opinions.add(op);
		}

		List<User> voters = new ArrayList<>();
		question.setVoters(voters);

		// Converts string team ids received from form data and assigns voters to
		// question and question to team
		for (int num : turnStringToIntArray(request.getTeams())) {
			Optional<Team> findById = teamDao.findById(num);
			if (findById != null) {
				Team team = findById.get();

				for (User member : team.getMembers()) {
					voters.add(member);
					member.getVoteQuestions().add(question);
				}
			}
		}

		question.setState(QuestionState.ACTIVE);
		question.setOwner(user);
		user.getAskQuestions().add(question);
		
		if (request.getIsIncludeMe() && !question.getVoters().contains(user)) {
			question.getVoters().add(user);
			user.getVoteQuestions().add(question);
		}
		
		Question q = questionDao.save(question);
		
		/*
		System.out.println("------------------------------");
		System.out.println(q.getVoters());
		for (User toSend : q.getVoters()) {
			emailUtil.sendQuestionEmail(q, toSend.getEmail());
		}*/
	}

	/**
	 * Creates a private question for each selected team in the addQuestion.html form.
	 * 
	 * @param request - form data received from addQuestion.html
	 * @param user - principal user
	 */
	public void addQuestionForEachTeam(@Valid AddQuestionDto request, User user) {
		List<Integer> teamIds = turnStringToIntArray(request.getTeams());
		
		for (int teamId : teamIds) {
			Optional<Team> teamById = teamDao.findById(teamId);
			if (teamById != null) {
				Team team = teamById.get();
				
				Question question = new Question();
				
				List<Opinion> opinions = new ArrayList<>();
				question.setOpinions(opinions);

				question.setText(request.getText() + "[" + team.getTitle() + "]");

				for (String option : request.getOptionParams()) {
					Opinion op = new Opinion();
					op.setQuestion(question);
					op.setText(option);
					opinions.add(op);
				}

				List<User> voters = new ArrayList<>();
				question.setVoters(voters);

				// Converts string team ids received from form data and assigns voters to
				// question and question to team

				for (User member : team.getMembers()) {
					voters.add(member);
					member.getVoteQuestions().add(question);
				}

				question.setState(QuestionState.PUBLIC);
				question.setOwner(user);
				user.getAskQuestions().add(question);

				if (request.getIsIncludeMe() && !question.getVoters().contains(user)) {
					question.getVoters().add(user);
					user.getVoteQuestions().add(question);
				}
					
				Question q = questionDao.save(question);
				
				/*
				System.out.println("------------------------------");
				System.out.println(q.getVoters());
				for (User toSend : q.getVoters()) {
					emailUtil.sendQuestionEmail(q, toSend.getEmail());
				}*/
			}
			
		}

	}

	/**
	 * Creates a public question that is visible for all users to vote on
	 * 
	 * @param request - form data received from addQuestion.html
	 * @param user - principal user
	 */
	public void addPublicQuestion(@Valid AddQuestionDto request, User user) {
		Question question = new Question();
		List<Opinion> opinions = new ArrayList<>();
		question.setOpinions(opinions);

		question.setText(request.getText());

		for (String option : request.getOptionParams()) {
			Opinion op = new Opinion();
			op.setQuestion(question);
			op.setText(option);
			opinions.add(op);
		}

		List<User> voters = new ArrayList<>();
		question.setVoters(voters);

		question.setState(QuestionState.ACTIVE);
		question.setOwner(user);
		user.getAskQuestions().add(question);

		Question q = questionDao.save(question);
		
		for (User toSend : question.getVoters()) {
			emailUtil.sendQuestionEmail(q, toSend.getEmail());
		}
		
	}
	
	//	DELETE
	////////////////////////////////////////////////////////////////
	/**
	 * Deletes a question based on the id
	 * @param name - user email. TODO: To be added security check
	 * @param id - question id
	 */
	public void deleteQuestion(String name, int id) {
		Question q = questionDao.findById(id).get();
		questionDao.delete(q);
	}
	
	//	UTILITY
	////////////////////////////////////////////////////////////////
	
	/**
	 * Used on the data transfer object. Convert String[] into int[] 
	 * @param array
	 * @return
	 */
	List<Integer> turnStringToIntArray(String[] array) {
		List<Integer> listNumbers = new ArrayList<>();
		for (String string : array) {
			try {
				int parseInt = Integer.parseInt(string);
				listNumbers.add(parseInt);
			} catch (Exception e) {
				System.out.println("Error: wrong passed team id");
			}

		}
		return listNumbers;
	}

	/**
	 * Checks if user already voted
	 * @param principal - user email
	 * @param q - Question
	 * @return
	 */
	public boolean checkIfUserVoted(String principal, Question q) {
		User user = userDao.findByEmail(principal);
		System.out.println(q.getVotedVoters());
		if(q.getVotedVoters().contains(user)) {
			System.out.println("User already voted");
			return true;
		};
		
		
		return false;
	}
}
