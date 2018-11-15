package com.bojan.teamvote.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Question implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int questionId;
	
	@NotEmpty(message = "You must enter a question description.")
	@Length(max=2048, message="Description cant be longer than 2048 characters")
	@Column(length=2048)
	String text;
	

	@ManyToOne(fetch=FetchType.EAGER,   cascade = CascadeType.REFRESH, targetEntity=User.class)	
	@JoinColumn(name="fk_quest_user")
	@JsonIgnore
	User questionUser;

	@OneToMany(targetEntity=Opinion.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "question", orphanRemoval = true)
	@JsonIgnore
	@Fetch(value = FetchMode.SUBSELECT)
	List<Opinion> opinion;

//	
	@Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Timestamp timestamp;



	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}



	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Question [id=" + questionId + ", text=" + text + ", timestamp=" + timestamp + "]";
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public User getQuestionUser() {
		return questionUser;
	}

	public void setQuestionUser(User questionUser) {
		this.questionUser = questionUser;
	}

	public List<Opinion> getOpinion() {
		return opinion;
	}

	public void setOpinion(List<Opinion> opinion) {
		this.opinion = opinion;
	}

}
