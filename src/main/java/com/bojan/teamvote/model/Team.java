package com.bojan.teamvote.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Team implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int teamId;
	
	String title;
	
	@ManyToOne(fetch=FetchType.EAGER,   cascade = CascadeType.REFRESH, targetEntity=User.class)	
	@JoinColumn(name="fk_user_team")
	@JsonIgnore
	User owner;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="USER_TEAMS", joinColumns= {
			@JoinColumn(name="TEAM_ID", referencedColumnName="teamId")
	}, inverseJoinColumns= {
			@JoinColumn(name="USER_ID", referencedColumnName="userId")
	})
	List<User> members;

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "Team [teamId=" + teamId + ", title=" + title + "]";
	}
	
	
	
}
	
