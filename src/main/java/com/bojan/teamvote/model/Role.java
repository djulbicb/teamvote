package com.bojan.teamvote.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Role can be ADMIN or USER. 
 * @author Bojan
 */
@Entity
public class Role implements Serializable{
	@Id
	private String name;
	
	@ManyToMany(mappedBy="roles") 
	private List<User> users;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public Role(String name, List<User> users) {
		super();
		this.name = name;
		this.users = users;
	}
	public Role() {
		// TODO Auto-generated constructor stub
	}
	public Role(String name) {
		super();
		this.name = name;
	}
}