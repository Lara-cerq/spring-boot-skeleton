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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class TradeControllerTest {

	@MockBean
	TradeService tradeService;

	@Autowired
	MockMvc mockMvc;

	Trade trade = new Trade(1, "Trade Account", "Type");

	@Test
	public void getAllTradesTest() throws Exception {
		List<Trade> trades = new ArrayList<>();
		trades.add(trade);
		Mockito.when(tradeService.getAllTrades()).thenReturn(trades);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/trade/list")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("tradeList", "name"))
				.andExpect(MockMvcResultMatchers.view().name("trade/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addTradeFormTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/trade/add")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("trade/add")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addTradeTest() throws Exception {
		Mockito.when(tradeService.addTrade(trade)).thenReturn(trade);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/trade/validate")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		Integer id = trade.getTradeId();
		trade.setType("test");
		Mockito.when(tradeService.findById(id)).thenReturn(Optional.of(trade));
		Mockito.when(tradeService.addTrade(trade)).thenReturn(trade);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/trade/update/{id}", id)

				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.view().name("trade/update"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("trade")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void updateTradeTest() throws Exception {
		Integer id = trade.getTradeId();
		trade.setAccount("test");
		Mockito.when(tradeService.findById(id)).thenReturn(Optional.of(trade));
		Mockito.when(tradeService.addTrade(trade)).thenReturn(trade);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/trade/update/{id}", id.toString())

				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void deleteTradeTest() throws Exception {
		Integer id = trade.getTradeId();
		Mockito.when(tradeService.findById(id)).thenReturn(Optional.of(trade));
		Mockito.when(tradeService.deleteTrade(trade)).thenReturn(true);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/trade/delete/{id}", id)
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}
}
