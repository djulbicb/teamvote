package com.bojan.teamvote.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.model.Role;
import com.bojan.teamvote.model.enums.QuestionState;

public interface QuestionDao extends JpaRepository<Question, Integer>{

	@Query(value="from Question q where q.state =:state")
	List<Question> findAllQuestionsByState(@Param("state") QuestionState state);
	
}
