package com.bojan.teamvote.model;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Opinion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int opinionId;
	
	String text;
	int count;
	
	@ManyToOne(fetch=FetchType.EAGER,   cascade = CascadeType.REFRESH, targetEntity=Question.class)	
	@JoinColumn(name="fk_quest_opinion")
	@JsonIgnore
	Question question;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JsonIgnore
	@JoinTable(name="OPINION_USER", joinColumns= {
			@JoinColumn(name="OPINION_ID", referencedColumnName="opinionId")
	}, inverseJoinColumns= {
			@JoinColumn(name="USER_ID", referencedColumnName="userId")
	})
	private List<User> users;
	
	
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
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
