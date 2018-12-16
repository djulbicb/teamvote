package com.bojan.teamvote.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bojan.teamvote.model.exceptions.AlreadyVotedException;
import com.bojan.teamvote.model.exceptions.DontHavePermission;
import com.bojan.teamvote.model.exceptions.UserNotFound;

@Controller
@ControllerAdvice
/**
 * Catches most common errors and shows appropriate views
 * @author Bojan
 */
public class ErrorController {

	/**
	 * User is trying to delete an object which is owned by somebody else
	 * @return
	 */
	@ExceptionHandler(DontHavePermission.class)
	public String dontHavePermission() {
		return "views/error/dontHavePermission";
	}
	
	/**
	 * Used in rest services. Email submitted doesnt exist in the database
	 * @return
	 */
	@ExceptionHandler(UserNotFound.class)
	public String userNotFound() {
		return "views/error/userNotFound";
	}
	
	/**
	 * JPA exception. Searched item was not found
	 * @return
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	public String entityNotFoundException() {
		return "views/error/entityNotFoundException";
	}
	
	/**
	 * Used in vote service. User has already voted
	 * @return
	 */
	@ExceptionHandler(AlreadyVotedException.class)
	public String alreadyVotedException() {
		return "views/error/alreadyVotedException";
	}
	
	
}
