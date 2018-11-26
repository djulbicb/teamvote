package com.bojan.teamvote.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bojan.teamvote.dao.TeamDao;
import com.bojan.teamvote.dao.UserDao;
import com.bojan.teamvote.model.Team;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.dto.AddTeamDto;

@Service
@Transactional
public class TeamService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	TeamDao teamDao;
	
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
			index ++;
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
				
			}else {
				List<Team> belongsTeam = new ArrayList<>();
				belongsTeam.add(team);
				teamMember.setBelongsTeams(belongsTeam);
			}	
		}
		
		userDao.save(owner);
	}

	
	
}
