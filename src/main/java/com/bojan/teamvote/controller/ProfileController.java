package com.bojan.teamvote.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.model.Setting;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.service.UserService;
import com.sun.xml.internal.org.jvnet.staxex.NamespaceContextEx.Binding;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	UserService userService;

	@GetMapping("")
	public String getProfile(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		System.out.println(user.getVotedQuestions());
		System.out.println(user.getVoteQuestions());
		model.addAttribute("user", user);
		return "views/profile/profile";
	}
	

	@GetMapping("privacy")
	public String getPrivacySetting(Model model, Principal principal) {
		Setting settings =  userService.findByEmail(principal.getName()).getSetting();
		model.addAttribute("request", settings);
		return "views/profile/privacy";
	}
	
	@GetMapping("deleteProfile")
	public String getDeleteProfile(Model model, Principal principal) {
		return "views/profile/deleteProfile";
	}
	
	@PostMapping("deleteProfile")
	public String postDeleteProfile(Model model, Principal principal) {
		userService.deleteUser(principal.getName());
		request.getSession().invalidate();
		return "redirect:/";
	}
	
	@PostMapping("updateSettings")
	public String postUpdateSettings(
			Model model,
			@Valid @ModelAttribute("request") Setting setting,
			BindingResult result,
			Principal principal
			) {
		if (result.hasErrors()) {
			for (ObjectError err : result.getAllErrors()) {
				System.out.println(err);
			}
			model.addAttribute("request", setting);
			return "views/profile/privacy";
		}
		
		userService.updateSetting(setting, principal.getName());
		
		return "redirect:/profile?updated";
	}
	
	@GetMapping("voted")
	public String getVoted(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		List<Question> votedQuestions = user.getVotedQuestions();
		System.out.println(votedQuestions);
		model.addAttribute("questions", votedQuestions);
		return "views/profile/voted";
	}

	@GetMapping("update")
	public String getUserInfo(Model model, Principal principal) {
		User user = userService.findByEmail(principal.getName());
		user.setPassword("");
		user.setAvatar("");
		model.addAttribute("user", user);
		return "views/profile/update";
	}

	@Autowired
	HttpServletRequest request;

	@PostMapping("update")
	public String postUserInfo(Model model, Principal principal, @Valid @ModelAttribute("user") User user, BindingResult result) {

		if (result.hasErrors()) {
			for (ObjectError err : result.getAllErrors()) {
				System.out.println(err);
			}
			model.addAttribute("user", user);
			return "views/profile/update";
		}

		MultipartFile image = user.getAvatarImage();

		if (image != null && !image.isEmpty()) {
			String extension = getExtensionOfFile(image.getOriginalFilename());
			String rndName = UUID.randomUUID().toString() + extension;

			user.setAvatar(rndName);

			String rootDirectory = request.getSession().getServletContext().getRealPath("/img/users");
			Path path = Paths.get(rootDirectory);

			if (!Files.exists(path)) {
				try {
					Files.createDirectories(path);
				} catch (IOException e) {
					// fail to create directory
					e.printStackTrace();
				}
			}

			path = Paths.get(path + "/" + user.getAvatar());

			System.out.println(path);

			try {
				image.transferTo(new File(path.toString()));
			} catch (Exception e) {
				throw new RuntimeException("Product image saving failed", e);
			}
		} 

		// END: Save image file

		userService.updateUser(principal.getName(),user);

		return "redirect:/profile";
	}
	

	private String getRandomAvatarImage() {
		Random rnd = new Random();
		int num = rnd.nextInt(3) +1;
		return "avatar"+num+".png";
	}
	
	public String getExtensionOfFile(String filename) {
		int lastIndexOf = filename.lastIndexOf(".");
		return filename.substring(lastIndexOf, filename.length());
	}

}
