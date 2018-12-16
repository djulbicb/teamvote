package com.bojan.teamvote.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.codehaus.groovy.runtime.metaclass.OwnedMetaClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bojan.teamvote.dao.TeamDao;
import com.bojan.teamvote.dao.UserDao;
import com.bojan.teamvote.model.Role;
import com.bojan.teamvote.model.Team;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.dto.AddTeamDto;
import com.bojan.teamvote.model.dto.UpdateTeamDto;
import com.bojan.teamvote.model.exceptions.DontHavePermission;

@Service
@Transactional
public class TeamService {

	@Autowired
	UserDao userDao;

	@Autowired
	TeamDao teamDao;

	// RETRIVE
	////////////////////////////////////////////////////////////////
	public Team findById(int id) {
		return teamDao.findById(id).get();
	}

	// CREATE
	////////////////////////////////////////////////////////////////
	/**
	 * Creates a team and fills it with users from request
	 * @param name - name of the team
	 * @param request - Data transfer object between view and controller
	 */
	public void addTeam(String name, @Valid AddTeamDto request) {

		// Check if duplicate emails
		Set<String> set = new HashSet<>();
		for (String email : request.getMemberEmails()) {
			set.add(email);
		}
		String[] tempEmails = new String[set.size()];
		int index = 0;
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String email = (String) iterator.next();
			tempEmails[index] = email;
			index++;
		}

		request.setMemberEmails(tempEmails);

		// Create team
		Team team = new Team();
		team.setTitle(request.getTeamName());

		User owner = userDao.findByEmail(name);
		team.setOwner(owner);

		List<Team> ownsTeams = owner.getOwnsTeams();
		ownsTeams.add(team);
		owner.setOwnsTeams(ownsTeams);

		List<User> members = new ArrayList<>();
		for (String email : request.getMemberEmails()) {
			User teamMember = userDao.findByEmail(email);
			members.add(teamMember);

			if (teamMember.getBelongsTeams() != null) {
				List<Team> belongsTeam = teamMember.getBelongsTeams();
				belongsTeam.add(team);

			} else {
				List<Team> belongsTeam = new ArrayList<>();
				belongsTeam.add(team);
				teamMember.setBelongsTeams(belongsTeam);
			}
		}

		userDao.save(owner);
	}

	// UPDATE
	////////////////////////////////////////////////////////////////
	/**
	 * Updates a team and fills it with users from request
	 * @param name - name of the team
	 * @param request - Data transfer object between view and controller
	 */
	public void updateTeam(int id, String name, @Valid UpdateTeamDto request) {
		Team team = teamDao.findById(id).get();
		team.setTitle(request.getTeamName());

		List<User> members = new ArrayList<>();
		for (String email : request.getMemberEmails()) {
			members.add(userDao.findByEmail(email));
		}

		List<User> oldMembers = team.getMembers();

		// FILTERS USERS TO BE DELETED
		List<User> toRemove = new ArrayList<>();

		for (User user : oldMembers) {
			if (members.contains(user)) {

			} else {
				toRemove.add(user);
			}
		}

		// FILTERS USERS TO BE ADDED
		List<User> toAdd = new ArrayList<>();
		for (User user : members) {
			if (oldMembers.contains(user)) {

			} else {
				toAdd.add(user);
				System.out.println("No");
			}
		}

		// REMOVES USERS
		for (User user : toRemove) {
			team.getMembers().remove(user);
			user.getBelongsTeams().remove(team);
			// userDao.save(user);
		}

		// ADDS USERS
		for (User user : toAdd) {
			team.getMembers().add(user);
			// user.getBelongsTeams().add(team);
		}

		teamDao.save(team);

	}

	// DELETE
	////////////////////////////////////////////////////////////////
	/**
	 * Removes user from a team
	 * @param name - email of the user
	 * @param id - id of the team
	 */
	public void removeFromTeam(String name, int id) {
		User user = userDao.findByEmail(name);
		Team team = teamDao.findById(id).get();

		user.getBelongsTeams().remove(team);
		team.getMembers().remove(user);

		teamDao.save(team);
		userDao.save(user);
	}

	/**
	 * Deletes a team
	 * @param name - user email 
	 * @param id - id of the team
	 * @throws DontHavePermission - if user isnt the owner or ADMIN throws error
	 */
	public void deleteTeam(String name, int id) throws DontHavePermission {
		User user = userDao.findByEmail(name);
		Team team = teamDao.findById(id).get();
		Role admin = new Role();
		admin.setName("ADMIN");

		if (team.getOwner() == user || user.getRoles().contains(admin)) {
			System.out.println("Deleting team");
			System.out.println(user.getPassword());
			/*
			 * team.setOwner(null); user.getOwnsTeams().remove(team); userDao.save(user);
			 * 
			 * for (User member : team.getMembers()) { team.getMembers().remove(member);
			 * member.getBelongsTeams().remove(team);
			 * 
			 * }
			 */
			teamDao.delete(team);

		} else {
			throw new DontHavePermission();
		}

	}

}
