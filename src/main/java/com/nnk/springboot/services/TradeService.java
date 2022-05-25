package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeService {

	@Autowired
	TradeRepository tradeRepository;

	public List<Trade> getAllTrades() {
		return tradeRepository.findAll();
	}

	public void addTrade(Trade trade) {
		tradeRepository.save(trade);
	}

	public Trade findById(Integer id) {
		return tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
	}

	public void deleteTrade(Trade trade) {
		tradeRepository.delete(trade);
	}

}
