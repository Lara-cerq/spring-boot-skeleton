package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;

import org.hibernate.service.spi.InjectService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingServiceTests {

	@Mock
	private RatingRepository ratingRepository;

	@InjectMocks
	private RatingService ratingService;

	Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

	@Test
	public void getAllRatingsTest() {
		List<Rating> ratingList = new ArrayList<>();
		ratingList.add(rating);
		rating = ratingService.addRating(rating);
		Mockito.when(ratingRepository.findAll()).thenReturn(ratingList);
		List<Rating> ratingListResultat = ratingService.getAllRatings();
		assertEquals(ratingList, ratingListResultat);
	}

	@Test
	public void addRatingTest() {
		Mockito.when(ratingRepository.save(rating)).thenReturn(rating);
		Rating ratingResult = ratingService.addRating(rating);
		assertEquals(ratingResult, rating);
	}

	@Test
	public void updateRatingTest() {
		rating.setOrderNumber(20);
		Mockito.when(ratingRepository.save(rating)).thenReturn(rating);
		Rating ratingResult = ratingService.addRating(rating);
		assertEquals(ratingResult, rating);
	}

	@Test
	public void deleteRatingTest() {
		boolean ratingResult = ratingService.deleteRating(rating);
		assertTrue(ratingResult);
	}
}
