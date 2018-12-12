package com.bojan.teamvote.model;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Opinion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int opinionId;
	
	String text;
	int count;
	
	@ManyToOne(fetch=FetchType.LAZY,   cascade = CascadeType.REFRESH, targetEntity=Question.class)	
	@JoinColumn(name="fk_quest_opinion")
	@JsonIgnore
	Question question;
	
	/*
	@ManyToMany(cascade=CascadeType.ALL)
	@JsonIgnore
	@JoinTable(name="OPINION_USER", joinColumns= {
			@JoinColumn(name="OPINION_ID", referencedColumnName="opinionId")
	}, inverseJoinColumns= {
			@JoinColumn(name="USER_ID", referencedColumnName="userId")
	})
	private List<User> users;
	*/
	
	@OneToMany(targetEntity = Vote.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "opinions", orphanRemoval = true)
	@JsonIgnore
	@Fetch(value = FetchMode.SUBSELECT)
	List<Vote> votes;
		
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public int getOpinionId() {
		return opinionId;
	}
	public void setOpinionId(int opinionId) {
		this.opinionId = opinionId;
	}
	public List<Vote> getVotes() {
		return votes;
	}
	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

}
