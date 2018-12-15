package com.bojan.teamvote.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bojan.teamvote.model.exceptions.DontHavePermission;

@Controller
@ControllerAdvice
public class ErrorController {

	@ExceptionHandler(DontHavePermission.class)
	public String dontHavePermission() {
		return "views/error/dontHavePermission";
	}
	/*
	@ExceptionHandler(EntityNotFoundException.class)
	public String entityNotFoundException() {
		return "views/error/entityNotFoundException";
	}
	*/

}
