package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class RatingController {

	private static Logger logger = LoggerFactory.getLogger(RatingController.class);

	@Autowired
	RatingService ratingService;

	@RequestMapping("/rating/list")
	public String home(Model model, Principal principal) {
		// find all Rating
		List<Rating> ratingList = ratingService.getAllRatings();
		// add to model
		model.addAttribute("ratingList", ratingList);
		model.addAttribute("name", principal.getName());
		return "rating/list";
	}

	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {
		return "rating/add";
	}

	@Transactional
	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		// check data valid
		if (!result.hasErrors()) {
			// save to db
			ratingService.addRating(rating);
			logger.info("NEW RATING ADDED");
			List<Rating> ratingList = ratingService.getAllRatings();
			model.addAttribute("ratingList", ratingList);
			// return Rating list
			return "redirect:/rating/list";
		}
		return "rating/add";
	}

	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Rating rating = ratingService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
		// add to model
		model.addAttribute("rating", rating);
		return "rating/update";
	}

	@Transactional
	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
			Model model) {
		// check required fields
		if (result.hasErrors()) {
			return "rating/update";
		}
		rating.setId(id);
		// call service to update Rating
		ratingService.addRating(rating);
		logger.info("RATING UPDATED");
		// return Rating list
		List<Rating> ratingList = ratingService.getAllRatings();
		model.addAttribute("ratingList", ratingList);
		// return rating list
		return "redirect:/rating/list";
	}

	@Transactional
	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		// Find Rating by Id
		Rating rating = ratingService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
		// delete the Rating
		ratingService.deleteRating(rating);
		logger.info("RATING DELETED");
		List<Rating> ratingList = ratingService.getAllRatings();
		model.addAttribute("ratingList", ratingList);
		// return to Rating list
		return "redirect:/rating/list";
	}
}
