package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

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
public class TradeController {

	private static Logger logger = LoggerFactory.getLogger(TradeController.class);

	@Autowired
	TradeService tradeService;

	@RequestMapping("/trade/list")
	public String home(Model model, Principal principal) {
		// find all Trade
		List<Trade> tradeList = tradeService.getAllTrades();
		// add to model
		model.addAttribute("tradeList", tradeList);
		model.addAttribute("name", principal.getName());
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addTradeForm(Trade bid) {
		return "trade/add";
	}

	@Transactional
	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		// check data valid
		if (!result.hasErrors()) {
			// save to db
			tradeService.addTrade(trade);
			List<Trade> tradeList = tradeService.getAllTrades();
			model.addAttribute("tradeList", tradeList);
			logger.info("TRADE ADDED");
			// return Trade list
			return "redirect:/trade/list";
		}
		return "trade/add";
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// get Trade by Id
		Trade trade = tradeService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
		// add to model
		model.addAttribute("trade", trade);
		// show to the form
		return "trade/update";
	}

	@Transactional
	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		// check required fields
		if (result.hasErrors()) {
			return "trade/update";
		}
		trade.setTradeId(id);
		// call service to update Trade
		tradeService.addTrade(trade);
		logger.info("TRADE UPDATED");
		// Trade list
		List<Trade> tradeList = tradeService.getAllTrades();
		model.addAttribute("tradeList", tradeList);
		// return to trade list
		return "redirect:/trade/list";
	}

	@Transactional
	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		// Find Trade by Id
		Trade trade = tradeService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
		// delete the Trade
		tradeService.deleteTrade(trade);
		logger.info("TRADE DELETED");
		List<Trade> tradeList = tradeService.getAllTrades();
		model.addAttribute("tradeList", tradeList);
		// return to Trade list
		return "redirect:/trade/list";
	}
}
