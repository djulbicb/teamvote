package com.bojan.teamvote.model.dto;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;

/**
 * Data Transfer Class used in creating the question. views:
 * profile/addQuestion.html
 * 
 * @author Bojan
 */
public class AddQuestionDto {

	@NotEmpty(message = "You must enter question name.")
	String text;
	@NotEmpty(message = "You must enter some options.")
	String[] optionParams;

	private boolean isPublic;

	private boolean isForEachTeam;

	private boolean isIncludeMe;

	String[] teams;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getOptionParams() {
		return optionParams;
	}

	public void setOptionParams(String[] optionParams) {
		this.optionParams = optionParams;
	}

	public String[] getTeams() {
		return teams;
	}

	public void setTeams(String[] teams) {
		this.teams = teams;
	}

	@Override
	public String toString() {
		return "AddQuestionDto [text=" + text + ", optionParams=" + Arrays.toString(optionParams) + ", isPublic=" + isPublic + ", isForEachTeam=" + isForEachTeam + ", isIncludeMe=" + isIncludeMe + ", teams=" + Arrays.toString(teams) + "]";
	}

	public void setIsPublic(boolean isPublic) {
		System.out.println("set public" + isPublic);
		this.isPublic = isPublic;
	}

	public void setIsForEachTeam(boolean isForEachTeam) {
		System.out.println("set public" + isForEachTeam);
		this.isForEachTeam = isForEachTeam;
	}

	public void setIsIncludeMe(boolean isIncludeMe) {
		System.out.println("set public" + isIncludeMe);
		this.isIncludeMe = isIncludeMe;
	}

	public boolean getIsForEachTeam() {
		System.out.println("is get each");
		return isForEachTeam;
	}

	public boolean getIsPublic() {
		System.out.println("is get public");
		return isPublic;
	}

	public boolean getIsIncludeMe() {
		System.out.println("is get include");
		return isIncludeMe;
	}
}
