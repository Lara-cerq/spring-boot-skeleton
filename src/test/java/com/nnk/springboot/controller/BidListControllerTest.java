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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BidListControllerTest {

	@MockBean
	private BidListService bidService;

	@Autowired
	MockMvc mockMvc;

	BidList bid = new BidList(1, "Account Test", "Type Test", 10d);

	@Test
	public void getAllBidListsTest() throws Exception {
		List<BidList> bids = new ArrayList<>();
		bids.add(bid);
		Mockito.when(bidService.getAllBidList()).thenReturn(bids);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bidList/list")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("bidLists", "name"))
				.andExpect(MockMvcResultMatchers.view().name("bidList/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addBidFormTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bidList/add")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		;
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("bidList/add")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addBidListTest() throws Exception {
		Mockito.when(bidService.addBidList(bid)).thenReturn(bid);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/bidList/validate")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		;
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("bidList/add")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		Integer id = bid.getBidlistId();
		bid.setBidQuantity(20d);
		Mockito.when(bidService.findById(id)).thenReturn(Optional.of(bid));
		Mockito.when(bidService.addBidList(bid)).thenReturn(bid);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bidList/update/{id}", id.toString())

				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		;
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.view().name("bidList/update"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void updateBidListTest() throws Exception {
		Integer id = bid.getBidlistId();
		bid.setBidQuantity(20d);
		Mockito.when(bidService.findById(id)).thenReturn(Optional.of(bid));
		Mockito.when(bidService.addBidList(bid)).thenReturn(bid);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/bidList/update/{id}", id.toString())

				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		;
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void deleteBidListTest() throws Exception {
		Integer id = bid.getBidlistId();
		Mockito.when(bidService.findById(id)).thenReturn(Optional.of(bid));
		Mockito.when(bidService.deleteBidList(bid)).thenReturn(true);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/bidList/delete/{id}", id)
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		;
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}
}
