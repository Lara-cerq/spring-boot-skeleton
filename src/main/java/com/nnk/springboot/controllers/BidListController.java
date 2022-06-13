package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

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
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class BidListController {

	private static Logger logger = LoggerFactory.getLogger(BidListController.class);

	@Autowired
	BidListService bidListService;

	@RequestMapping(value = "/bidList/list", method = RequestMethod.GET)
	public String home(Model model, Principal principal) {
		// find all bidLists
		List<BidList> bidLists = bidListService.getAllBidList();
		model.addAttribute("bidLists", bidLists);
		model.addAttribute("name", principal.getName());
		return "bidList/list";
	}

	@GetMapping("/bidList/add")
	public String addBidForm(BidList bid) {
		return "bidList/add";
	}

	@Transactional
	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		// check if data is valid
		if (!result.hasErrors()) {
			// add in DB
			bidListService.addBidList(bid);
			logger.info("NEW BID LIST ADDED");
			List<BidList> bidLists = bidListService.getAllBidList();
			model.addAttribute("bidLists", bidLists);
			// return list
			return "redirect:/bidList/list";
		}
		return "bidList/add";
	}

	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// get Bid by Id
		BidList bid = bidListService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid bid list Id:" + id));
		model.addAttribute("bidList", bid);
		return "bidList/update";
	}

	@Transactional
	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		// check required fields
		if (result.hasErrors()) {
			return "bidList/update";
		}
		bidList.setBidlistId(id);
		// to update Bid
		bidListService.addBidList(bidList);
		logger.info("BID LIST UPDATED");
		// list Bid
		List<BidList> bidLists = bidListService.getAllBidList();
		model.addAttribute("bidLists", bidLists);
		// return to the list
		return "redirect:/bidList/list";
	}

	@Transactional
	@GetMapping(value = "/bidList/delete/{id}")
	public String deleteBid(@PathVariable(required = true, name = "id") Integer id, Model model) {
		// Find Bid by Id
		Optional<BidList> bid = bidListService.findById(id);
		// delete the bid
		bidListService.deleteBidList(bid.get());
		logger.info("BID LIST DELETED");
		List<BidList> bidLists = bidListService.getAllBidList();
		model.addAttribute("bidLists", bidLists);
		// return to Bid list
		return "redirect:/bidList/list";
	}
}
