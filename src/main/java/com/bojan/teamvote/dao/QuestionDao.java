package com.bojan.teamvote.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.model.Role;

public interface QuestionDao extends JpaRepository<Question, Integer>{

}
