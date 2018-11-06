package com.bojan.teamvote.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bojan.teamvote.dao.QuestionDao;
import com.bojan.teamvote.model.Question;

@Service
@Transactional
public class QuestionService {

	@Autowired
	QuestionDao questionDao;

	public Question addQuestion(Question q) {
		Question question = questionDao.save(q);
		return question;
	}

}
