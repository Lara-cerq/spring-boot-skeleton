package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/user/list")
	public String home(Model model) {
		List<User> usersList = userService.getAllUsers();
		model.addAttribute("users", usersList);
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUser(User bid) {
		return "user/add";
	}

	@Transactional
	@PostMapping("/user/validate")
	public String validate(@Valid User user, BindingResult result, Model model, Errors errors,
			RedirectAttributes redirectAttributes) {
		String password = user.getPassword();
		if (!result.hasErrors()) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(password));
			userService.addUser(user);
			logger.info("USER CREATED");
			List<User> usersList = userService.getAllUsers();
			model.addAttribute("users", usersList);
			return "redirect:/user/list";
		}
		return "user/add";

	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = userService.findUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		;
		user.setPassword("");
		model.addAttribute("user", user);
		return "user/update";
	}

	@Transactional
	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		String password = user.getPassword();
		if (result.hasErrors()) {
			return "user/update";
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));
		user.setId(id);
		userService.addUser(user);
		logger.info("USER UPDATED");
		List<User> usersList = userService.getAllUsers();
		model.addAttribute("users", usersList);
		return "redirect:/user/list";
	}

	@Transactional
	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		User user = userService.findUserById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		;
		userService.deleteUser(user);
		logger.info("USER DELETED");
		List<User> usersList = userService.getAllUsers();
		model.addAttribute("users", usersList);
		return "redirect:/user/list";
	}
}
