package com.bojan.teamvote.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bojan.teamvote.model.Team;
import com.bojan.teamvote.model.User;

public interface UserDao extends JpaRepository<User, Integer>{

	/**
	 * Used in register html page. If user name already exists send error
	 * @param username - username to be checked in db
	 * @return true or false
	 */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.name = :username")
    boolean ifExistsByName(@Param("username") String username);
    
    /**
      * Used in register html page. If user email already exists send error
	 * @param username - username to be checked in db
	 * @return true or false
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean ifExistsByEmail(@Param("email") String email);

    /**
     * Find User entity by email
     * @param email
     * @return
     */
	User findByEmail(String email);

	/**
	 * Used in rest controllers
	 * Used in create team and update team views. Searches users in database and fills list box
	 * @param email - user email
	 * @param firstName - user first name
	 * @param lastName - user last name
	 * @return List<User>
	 */
	@Query("Select u from User u WHERE u.email like %:email% and u.firstName like %:firstName% and u.lastName like %:lastName% and u.setting.addTeam=false")
	List<User> findUsersByFilters(@Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName);
	
	@Deprecated
	@Query("Select u from User u WHERE u.email like %:email%")
	List<User> findByFirstNameLike(@Param("email") String email);

	
}
