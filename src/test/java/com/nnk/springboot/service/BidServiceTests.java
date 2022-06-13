package com.nnk.springboot.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidServiceTests {

	@InjectMocks
	private BidListService bidListService;
	
	@Mock
	private BidListRepository bidListRepository;

	BidList bid = new BidList("Account Test", "Type Test", 10d);
	
	@Test
	public void getAllBidListsTest() {
		List<BidList> bidList= new ArrayList<>();
		bidList.add(bid);
		bid = bidListService.addBidList(bid);
		Mockito.when(bidListRepository.findAll()).thenReturn(bidList);
		List<BidList> bidListsResult= bidListService.getAllBidList();
		assertEquals(bidListsResult, bidList);
	}
	
	@Test
	public void saveBidListTest() {
		Mockito.when(bidListRepository.save(bid)).thenReturn(bid);
		BidList bidListResult = bidListService.addBidList(bid);
		assertEquals(bid, bidListResult);
	}
	
	@Test
	public void updateBidListTest() {
		bid.setBidQuantity(20d);
		Mockito.when(bidListRepository.save(bid)).thenReturn(bid);
		BidList bidListResult = bidListService.addBidList(bid);
		assertEquals(bid, bidListResult);
	}
	
	@Test
	public void deleteBidListTest() {
		boolean bidListResultat = bidListService.deleteBidList(bid);
		assertTrue(bidListResultat);
	}
}
