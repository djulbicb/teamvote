package com.bojan.teamvote.service;

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

@Service
@Transactional
public class QuestionService {

	@Autowired
	QuestionDao questionDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	TeamDao teamDao;
	
	public Question addQuestion(Question q) {
		Question question = questionDao.save(q);
		return question;
	}

	public void addQuestion(@Valid AddQuestionDto request, User user) {
		Question question=new Question();
		List<Opinion> opinions = new ArrayList<>();
		question.setOpinion(opinions );
		
		question.setText(request.getText());
		
		for (String option : request.getOptionParams()) {
			Opinion op = new Opinion();
			op.setQuestion(question);
			op.setText(option);
			opinions.add(op);
		}
		
		for (String strTeamId : request.getTeams()) {
			try {
				int parseInt = Integer.parseInt(strTeamId);
				Optional<Team> findById = teamDao.findById(parseInt);
				if (findById != null) {
					Team team = findById.get();
					for (User member : team.getMembers()) {
						
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		question.setQuestionUser(user);
		user.getQuestions().add(question);
		
		questionDao.save(question);
	}

	public void addQuestionForEachTeam(@Valid AddQuestionDto request, User user) {
		// TODO Auto-generated method stub
		
	}

	public void addPublicQuestion(@Valid AddQuestionDto request, User user) {
		// TODO Auto-generated method stub
		
	}

}
