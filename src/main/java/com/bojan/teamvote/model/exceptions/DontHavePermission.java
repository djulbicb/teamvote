package com.bojan.teamvote.model.exceptions;
/**
 * Used in QuestionService, VoteService. If user is not the owner of entity or user isnt ADMIN throw exception
 * @author Bojan
 */
public class DontHavePermission extends Exception {

}
