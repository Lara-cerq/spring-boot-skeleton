package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {

	@Autowired
	BidListRepository bidListRepository;

	public List<BidList> getAllBidList() {
		return bidListRepository.findAll();
	}

	public void addBidList(BidList bidList) {
		bidListRepository.save(bidList);
	}

	public BidList findById(Integer id) {
		return bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid list Id:" + id));
	}

	public void deleteBidList(BidList bidList) {
		bidListRepository.delete(bidList);
	}
}
