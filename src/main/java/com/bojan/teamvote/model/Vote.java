package com.bojan.teamvote.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int voteId;
	
	String code;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH, targetEntity=User.class)	
	@JsonIgnore
	User user;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH, targetEntity=Question.class)	
	@JsonIgnore
    Question question;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.REFRESH, targetEntity=Opinion.class)	
	@JsonIgnore
    Opinion opinions;

	public int getVoteId() {
		return voteId;
	}

	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Opinion getOpinions() {
		return opinions;
	}

	public void setOpinions(Opinion opinions) {
		this.opinions = opinions;
	}
}
