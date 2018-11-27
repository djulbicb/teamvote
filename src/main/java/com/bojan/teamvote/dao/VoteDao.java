package com.bojan.teamvote.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.Vote;

public interface VoteDao extends JpaRepository<Vote, Integer>{

	
}
