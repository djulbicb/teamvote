package com.bojan.teamvote.model.dto;

import java.util.Arrays;

import javax.validation.constraints.NotEmpty;

public class AddTeamDto {
	
	@NotEmpty(message = "You must enter team name.")
	String teamName;
	@NotEmpty(message = "You must enter team members.")
	String[] memberEmails;
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String[] getMemberEmails() {
		return memberEmails;
	}
	public void setMemberEmails(String[] memberEmails) {
		this.memberEmails = memberEmails;
	}
	@Override
	public String toString() {
		return "AddTeamDto [teamName=" + teamName + ", memberEmails=" + Arrays.toString(memberEmails) + "]";
	}
	
	
}
