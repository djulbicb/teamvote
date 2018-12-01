package com.bojan.teamvote.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.multipart.MultipartFile;

import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.service.QuestionService;
import com.bojan.teamvote.service.UserService;

@Controller
public class IndexController {

	@Autowired
	UserService userService;

	@Autowired
	QuestionService questionService;
	
	/**
	 * Shows all public questions. Anyone who is logged in can vote on them. If user already voted show statistics
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String getIndex(
			Model model
			) {
		
		List<Question> questions = questionService.findAllPublicQuestions();
		model.addAttribute("questions", questions);
		
		return "views/index";
	}

	@GetMapping("/login")
	public String getLogin(Model model, @RequestParam(name = "register", required = false) String register) {
		if (register != null) {
			System.out.println("Hello");
			model.addAttribute("msg", "Registration was succesfull");
		}
		return "views/login";
	}

	@GetMapping("/register")
	public String getRegister(Model model) {
		User user = new User();

		model.addAttribute("user", user);
		return "views/register";
	}
	
	@GetMapping("/test")
	public String test(Model model, HttpServletRequest request) {
		String rootDirectory = request.getSession().getServletContext().getRealPath("/img/users");
		Path path = Paths.get(rootDirectory);
	        
	        if (!Files.exists(path)) {
	            try {
	                Files.createDirectories(path);
	            } catch (IOException e) {
	                //fail to create directory
	                e.printStackTrace();
	            }
	        }

		
		return "views/test";
	}
	

	@PostMapping("/register")
	public String postRegister(@Valid @ModelAttribute(name = "user") User user, BindingResult result, Model model, HttpServletRequest request) {
		// Validate - Check if any errors in form, or if user already exists
		if (result.hasErrors()) {
			return "views/register";
		}

		if (userService.ifExistsByName(user.getName())) {
			model.addAttribute("user", user);
			model.addAttribute("msg", "Username already exists, please change.");
			return "views/register";
		}
		;

		if (userService.ifExistsByEmail(user.getEmail())) {
			model.addAttribute("user", user);
			model.addAttribute("msg", "Email already exists, please change.");
			return "views/register";
		}
		;
		// END: Validate - Check if any errors in form, or if user already exists

		// Save image file
		// Check if image was uploaded. Save this image or assign random avatar image
		// http://localhost:8080/img/users/avatar2.jpg
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
		                //fail to create directory
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
		}else{
			user.setAvatar(getRandomAvatarImage());
		}

		// END: Save image file
		
		userService.addUser(user);
		 
		return "redirect:/login?register";
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
