package com.bojan.teamvote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Used in User entity. Controls user privacy in app. User can be blocked being added into a group or receiving emails.
 * @author Bojan
 *
 */
@Entity
public class Setting {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int settingsId;

	@Column(nullable=false, columnDefinition="boolean default false")
	@NotNull
	boolean sendEmail;
	
	@Column(nullable=false, columnDefinition="boolean default false")
	@NotNull
	boolean addTeam;
	
	@OneToOne(mappedBy="setting", orphanRemoval=true)
	@JsonIgnore
	User user;

	public int getSettingsId() {
		return settingsId;
	}

	public void setSettingsId(int settingsId) {
		this.settingsId = settingsId;
	}

	public boolean getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public boolean getAddTeam() {
		return addTeam;
	}

	public void setAddTeam(boolean addTeam) {
		this.addTeam = addTeam;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
}
