package com.bojan.teamvote.controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bojan.teamvote.model.Team;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.dto.AddTeamDto;
import com.bojan.teamvote.model.dto.UpdateTeamDto;
import com.bojan.teamvote.model.exceptions.DontHavePermission;
import com.bojan.teamvote.service.TeamService;
import com.bojan.teamvote.service.UserService;

@Controller
@RequestMapping("/profile")
public class TeamController {

	@Autowired
	UserService userService;

	@Autowired
	TeamService teamService;
	
	// RETRIVE VIEWS
	////////////////////////////////////////////////////////////////
	/**
	 * Shows all teams user has created
	 */
	@GetMapping("showCreatedTeams")
	public String getCreatedTeams(
			Model model,
			Principal principal) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("teams", user.getOwnsTeams());
		return "views/profile/showCreatedTeams";
	}
	
	/**
	 * Shows all teams user is a part of
	 */
	@GetMapping("showBelongsTeams")
	public String getBelongsTeams(
			Model model,
			Principal principal) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("teams", user.getBelongsTeams());
		return "views/profile/showBelongsTeams";
	}

	// CREATE
	////////////////////////////////////////////////////////////////

	/**
	 * Shows create a team view
	 */
	@GetMapping("addTeam")
	public String getAddTeam(Model model) {
		AddTeamDto request = new AddTeamDto();
		model.addAttribute("request", request);
		return "views/profile/addTeam";
	}
	
	/**
	 * Proceeds to save form data as a new team
	 */
	@PostMapping("addTeam")
	public String postAddTeam(
			@Valid @ModelAttribute("request") AddTeamDto request, 
			BindingResult results,
			Model model,
			Principal principal) {
		
		if (results.hasErrors()) {
			System.out.println("Error");
			model.addAttribute("request", request);
			return "views/profile/addTeam";
		}
		System.out.println("------------");
		System.out.println(request);
		System.out.println("------------");
		teamService.addTeam(principal.getName(), request);
		return "redirect:/profile";
	}
	
	// UPDATE
	////////////////////////////////////////////////////////////////
	
	/**
	 * Shows update team view
	 */
	@GetMapping("viewTeam/{id}")
	public String postViewTeam(
			@PathVariable("id") int id,
			Model model,
			Principal principal){
		
		Team team = teamService.findById(id);
		UpdateTeamDto request = new UpdateTeamDto();
		request.setTeamName(team.getTitle());
		
		request.setTeamId(team.getTeamId());
		
		List<User> members = team.getMembers();
		String[] memberArray = new String[members.size()];
		for (int i = 0; i < memberArray.length; i++) {
			memberArray[i] = members.get(i).getEmail();
		}
		request.setMemberEmails(memberArray);
		System.out.println(memberArray);
		
		model.addAttribute("request", request);
		
		return "views/profile/viewTeam";
	}
	
	/**
	 * Proceeds to update team based on the form data
	 */
	@PostMapping("viewTeam/{id}")
	public String postUpdateTeam(
			@Valid @ModelAttribute("request") UpdateTeamDto request,
			BindingResult results,
			@PathVariable("id") int id,
			Model model,
			Principal principal) {
		
		if (results.hasErrors()) {
			System.out.println(request.getTeamId());
			System.out.println("Error");
			model.addAttribute("request", request);
			return "views/profile/viewTeam";
		}
		
		System.out.println(request);
		System.out.println(id);
		request.setTeamId(id);
		teamService.updateTeam(id, principal.getName(), request);
		return "redirect:/profile/showCreatedTeams";
	}
	
	// DELETE
	////////////////////////////////////////////////////////////////
	
	/**
	 * User can remove oneself from the team in the showBelongTeam page
	 */
	@PostMapping("removeFromTeam/{id}")
	public String postRemoveFromTeam(
			@PathVariable("id") int id,
			Principal principal) {
		teamService.removeFromTeam(principal.getName(), id);
		return "redirect:/profile/showBelongsTeams";
	}

	/**
	 * Proceeds to delete a team
	 */
	@PostMapping("deleteTeam/{id}")
	public String postDeleteTeam(
			@PathVariable("id") int id,
			Principal principal) throws DontHavePermission {
		teamService.deleteTeam(principal.getName(), id);
		return "redirect:/profile/showCreatedTeams";
	}
}
