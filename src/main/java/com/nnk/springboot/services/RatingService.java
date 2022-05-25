package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {

	@Autowired
	RatingRepository ratingRepository;

	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}

	public void addRating(Rating rating) {
		ratingRepository.save(rating);
	}

	public Rating findById(Integer id) {
		return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
	}

	public void deleteRating(Rating rating) {
		ratingRepository.delete(rating);
	}
}
