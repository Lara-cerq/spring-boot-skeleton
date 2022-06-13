package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

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

	public Trade addTrade(Trade trade) {
		return tradeRepository.save(trade);
	}

	public Optional<Trade> findById(Integer id) {
		return tradeRepository.findById(id);
	}

	public boolean deleteTrade(Trade trade) {
		tradeRepository.delete(trade);
		return true;
	}

}
