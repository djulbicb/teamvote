package com.bojan.teamvote.dao;

import java.util.List;

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

	User findByEmail(String email);

	//@Query("Select u from User u WHERE u.email like %:email% and u.firstName like %:firstName% and u.lastName like %:lastName%")
	//@Query("Select u from User u WHERE u.email LIKE CONCAT ('%', :email, '%') and u.firstName LIKE CONCAT ('%', :firstName, '%') and u.lastName LIKE CONCAT ('%', :lastName, '%')")
	@Query("Select u from User u WHERE u.email like %:email% and u.firstName like %:firstName% and u.lastName like %:lastName%")
	List<User> findUsersByFilters(@Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName);
	
	@Query("Select u from User u WHERE u.email like %:email%")
	List<User> findByFirstNameLike(@Param("email") String email);
	
}
