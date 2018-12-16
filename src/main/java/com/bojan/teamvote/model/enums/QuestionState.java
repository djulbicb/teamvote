package com.bojan.teamvote.model.enums;
/*
 * If Public any user logged in can vote on it
 * If Active any user assigned to the question can vote on the question
 * 
 * Archived isnt implemented
 */
public enum QuestionState {
	PUBLIC, ACTIVE, ARCHIVED
}
