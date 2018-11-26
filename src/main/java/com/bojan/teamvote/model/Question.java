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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;

import org.hibernate.annotations.ColumnDefault;
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
	@JoinColumn(name="quest_user")
	@JsonIgnore
	User owner;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name = "QuestionsUser", joinColumns = @JoinColumn(name = "questionId"), inverseJoinColumns = @JoinColumn(name = "userId"))
	@JsonIgnore
	List<User> voters;
	
	@OneToMany(targetEntity=Opinion.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "question", orphanRemoval = true)
	@JsonIgnore
	@Fetch(value = FetchMode.SUBSELECT)
	List<Opinion> opinions;

	@Column(name = "timestamp", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	Timestamp timestamp;

	boolean isPublic;

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


	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public List<User> getVoters() {
		return voters;
	}

	public void setVoters(List<User> voters) {
		this.voters = voters;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Opinion> getOpinions() {
		return opinions;
	}

	public void setOpinions(List<Opinion> opinions) {
		this.opinions = opinions;
	}

}
