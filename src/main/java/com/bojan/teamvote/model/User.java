package com.bojan.teamvote.model;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int userId;
	
	@Email
	@NotEmpty
	@Column(unique=true)
	private String email;
	
	@NotEmpty
	private String name;
	
	@Size(min=3)
	private String password;
	
	@OneToMany(targetEntity=Question.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "questionUser", orphanRemoval = true)
	@JsonIgnore
	@Fetch(value = FetchMode.SUBSELECT)
	List<Question> questions;
	
	@ManyToMany(mappedBy="users") 
	private List<Opinion> opinions;
	
	/*
	// mapped by pokazuje ko je vlasnik
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private List<Task> tasks;
	*/
	
	// ovaj prvi gleda gore property email i kreira kolonu
	// drugi gleda polje u detetu
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="USER_ROLES", joinColumns= {
			@JoinColumn(name="USER_ID", referencedColumnName="userId")
	}, inverseJoinColumns= {
			@JoinColumn(name="ROLE_EMAIL", referencedColumnName="name")
	})
	private List<Role> roles;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public User(String email, String name, String password, List<Role> roles) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;

		this.roles = roles;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", password=" + password + ", roles="
				+ roles + "]";
	}

	public User(@Email @NotEmpty String email, @NotEmpty String name, @Size(min = 3) String password) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
	}



	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<Opinion> getOpinions() {
		return opinions;
	}

	public void setOpinions(List<Opinion> opinions) {
		this.opinions = opinions;
	}
}
