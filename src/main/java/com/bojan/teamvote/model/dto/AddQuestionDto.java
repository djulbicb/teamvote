package com.bojan.teamvote.model.dto;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotEmpty;
/**
 * Data Transfer Class used in creating the question.
 * views: profile/addQuestion.html
 * @author Bojan
 */
public class AddQuestionDto {
	
	@NotEmpty(message = "You must enter question name.")
	String text;
	@NotEmpty(message = "You must enter some options.")
	String[] optionParams;

	private boolean isPublic;
	
	private boolean isForEachTeam;
	
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

	public boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean getIsForEachTeam() {
		return isForEachTeam;
	}

	public void setIsForEachTeam(boolean isForEachTeam) {
		this.isForEachTeam = isForEachTeam;
	}

	@Override
	public String toString() {
		return "AddQuestionDto [text=" + text + ", optionParams=" + Arrays.toString(optionParams) + ", isPublic=" + isPublic + ", isForEachTeam=" + isForEachTeam + ", teams=" + Arrays.toString(teams) + "]";
	}

}
