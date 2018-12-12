package com.bojan.teamvote.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bojan.teamvote.model.Team;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.model.dto.AddTeamDto;
import com.bojan.teamvote.service.TeamService;
import com.bojan.teamvote.service.UserService;

@Controller
@RequestMapping("/profile")
public class TeamController {

	@Autowired
	UserService userService;

	@Autowired
	TeamService teamService;
	
	@GetMapping("showCreatedTeams")
	public String getCreatedTeams(
			Model model,
			Principal principal) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("teams", user.getOwnsTeams());
		return "views/profile/showCreatedTeams";
	}
	
	@GetMapping("showBelongsTeams")
	public String getBelongsTeams(
			Model model,
			Principal principal) {
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("teams", user.getBelongsTeams());
		return "views/profile/showBelongsTeams";
	}

	
	@GetMapping("addTeam")
	public String getAddTeam(Model model) {
		AddTeamDto request = new AddTeamDto();
		model.addAttribute("request", request);
		return "views/profile/addTeam";
	}
	
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
	
	@GetMapping("test")
	public String getTest() {
		
		List<User> findUsersByFilters = userService.findUsersByFilters("ptt", "", "");
		System.out.println(findUsersByFilters);
		return "views/profile/manageTeam";
	}
	
}
