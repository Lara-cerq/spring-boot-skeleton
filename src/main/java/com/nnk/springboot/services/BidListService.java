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

	public BidList addBidList(BidList bidList) {
		return bidListRepository.save(bidList);
	}

	public Optional<BidList> findById(Integer id) {
		return bidListRepository.findById(id);
	}

	public boolean deleteBidList(BidList bidList) {
		bidListRepository.delete(bidList);
		return true;
	}
}
