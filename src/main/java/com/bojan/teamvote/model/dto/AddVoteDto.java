package com.bojan.teamvote.model.dto;

import java.util.Arrays;

/**
 * Data Transfer Class used in creating votes in views:
 * vote/voteQuestion.html
 * 
 * @author Bojan
 */
public class AddVoteDto {

	int questionId;
	String userEmail;
	String[] selVotes;

	public String[] getSelVotes() {
		return selVotes;
	}

	public void setSelVotes(String[] selVotes) {
		this.selVotes = selVotes;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Override
	public String toString() {
		return "AddVoteDto [questionId=" + questionId + ", userEmail=" + userEmail + ", selVotes=" + Arrays.toString(selVotes) + "]";
	}
	
	
	
}
