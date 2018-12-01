
package com.bojan.teamvote.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bojan.teamvote.model.Team;

public interface TeamDao extends JpaRepository<Team, Integer>{

}
