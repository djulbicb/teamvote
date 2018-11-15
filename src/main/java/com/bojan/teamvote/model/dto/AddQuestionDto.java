package com.bojan.teamvote.model.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class AddQuestionDto {
	
	@NotEmpty(message = "You must enter question name.")
	String text;
	@NotEmpty(message = "You must enter some options.")
	String[] optionParams;


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "AddQuestionDto [text=" + text + ", optionParams=" + optionParams + "]";
	}

	public String[] getOptionParams() {
		return optionParams;
	}

	public void setOptionParams(String[] optionParams) {
		this.optionParams = optionParams;
	}

}
