package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceTests {

	@InjectMocks
	private TradeService tradeService;

	@Mock
	private TradeRepository tradeRepository;

	Trade trade = new Trade("Trade Account", "Type");

	@Test
	public void getAllTradesTest() {
		List<Trade> tradeList = new ArrayList<>();
		tradeList.add(trade);
		trade = tradeService.addTrade(trade);
		Mockito.when(tradeRepository.findAll()).thenReturn(tradeList);
		List<Trade> tradeListResult = tradeService.getAllTrades();
		assertEquals(tradeList, tradeListResult);
	}

	@Test
	public void addTradeTest() {
		Mockito.when(tradeRepository.save(trade)).thenReturn(trade);
		Trade tradeResultat = tradeService.addTrade(trade);
		assertEquals(trade, tradeResultat);
	}

	@Test
	public void updateTradeTest() {
		trade.setAccount("Trade Account Update");
		Mockito.when(tradeRepository.save(trade)).thenReturn(trade);
		Trade tradeResultat = tradeService.addTrade(trade);
		assertEquals(trade, tradeResultat);
	}

	@Test
	public void findTradeByIdTest() {
		Integer id = trade.getTradeId();
		Optional<Trade> tradeOptional = tradeService.findById(id);
		Optional<Trade> tradeResultat = tradeRepository.findById(id);
		Mockito.when(tradeRepository.findById(id)).thenReturn(Optional.of(trade));
		assertEquals(tradeOptional, tradeResultat);	}

	@Test
	public void deleteTradeTest() {
		boolean tradeResultat = tradeService.deleteTrade(trade);
		assertTrue(tradeResultat);
	}
}
