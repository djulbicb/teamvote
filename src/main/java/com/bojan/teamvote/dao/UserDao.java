package com.bojan.teamvote.dao;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bojan.teamvote.model.User;

public interface UserDao extends JpaRepository<User, Integer>{

}
