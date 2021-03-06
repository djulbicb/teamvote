package com.bojan.teamvote.model.dto;

import java.util.Arrays;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/**
 * Data Transfer Class used in creating teams in views:
 * profile/addTeam.html
 * 
 * @author Bojan
 */
public class UpdateTeamDto {
	
	@NotNull
	int teamId;
	
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
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	
	
}
