package com.nnk.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RatingControllerTest {

	@MockBean
	RatingService ratingService;

	@Autowired
	MockMvc mockMvc;

	Rating rating = new Rating(1, "Moodys Rating", "Sand PRating", "Fitch Rating", 10);

	@Test
	public void getAllRatingsTest() throws Exception {
		List<Rating> ratings = new ArrayList<>();
		ratings.add(rating);
		Mockito.when(ratingService.getAllRatings()).thenReturn(ratings);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/rating/list")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("ratingList", "name"))
				.andExpect(MockMvcResultMatchers.view().name("rating/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addRatingFormTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/rating/add")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("rating/add")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addRatingTest() throws Exception {
		Mockito.when(ratingService.addRating(rating)).thenReturn(rating);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/rating/validate")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		Integer id = rating.getId();
		rating.setOrderNumber(20);
		Mockito.when(ratingService.findById(id)).thenReturn(Optional.of(rating));
		Mockito.when(ratingService.addRating(rating)).thenReturn(rating);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/rating/update/{id}", id)
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.view().name("rating/update"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("rating")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void updateRatingTest() throws Exception {
		Integer id = rating.getId();
		rating.setOrderNumber(20);
		Mockito.when(ratingService.findById(id)).thenReturn(Optional.of(rating));
		Mockito.when(ratingService.addRating(rating)).thenReturn(rating);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/rating/update/{id}", id.toString())
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void deleteRatingTest() throws Exception {
		Integer id = rating.getId();
		Mockito.when(ratingService.findById(id)).thenReturn(Optional.of(rating));
		Mockito.when(ratingService.deleteRating(rating)).thenReturn(true);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/rating/delete/{id}", id)
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}
}
