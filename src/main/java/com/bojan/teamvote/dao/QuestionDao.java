package com.bojan.teamvote.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.model.Role;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.enums.QuestionState;

public interface QuestionDao extends JpaRepository<Question, Integer>{

	@Query(value="from Question q where q.state =:state")
	List<Question> findAllQuestionsByState(@Param("state") QuestionState state);


	/*
	// "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c WHERE c.name = :companyName"
	@Query("SELECT CASE WHEN COUNT(votedVoters)")
	boolean checkIfUserVoted(@Param("user") User user, @Param("question")Question q);
*/
	
}
