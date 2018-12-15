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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int voteId;
	
	String code;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity=User.class)
	@JsonIgnore
	User user;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity=Question.class)	
	@JsonIgnore
    Question question;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.PERSIST, targetEntity=Opinion.class)	
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

	@Override
	public String toString() {
		return "Vote [voteId=" + voteId + ", code=" + code + ", user=" + user.getEmail() + ", question=" + question.getText() + ", opinions=" + opinions.getCount() + "]";
	}
}
