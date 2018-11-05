package com.bojan.teamvote.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bojan.teamvote.model.Role;

public interface RoleDao extends JpaRepository<Role, String>{

}
