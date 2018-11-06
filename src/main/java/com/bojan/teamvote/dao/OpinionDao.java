package com.bojan.teamvote.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bojan.teamvote.model.Opinion;
import com.bojan.teamvote.model.Role;

public interface OpinionDao extends JpaRepository<Opinion, Integer>{

}
