package com.bojan.teamvote.dao;

import javax.persistence.criteria.CriteriaBuilder.In;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojan.teamvote.model.User;

public interface UserDao extends JpaRepository<User, Integer>{

	// Check if Exists
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.name = :username")
    boolean ifExistsByName(@Param("username") String username);
    
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean ifExistsByEmail(@Param("email") String email);
	
}
