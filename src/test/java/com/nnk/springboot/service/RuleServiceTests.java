package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleServiceTests {

	@InjectMocks
	private RuleService ruleService;

	@Mock
	private RuleNameRepository ruleRepository;

	RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

	@Test
	public void getAllRulesTest() {
		List<RuleName> ruleList = new ArrayList<>();
		ruleList.add(rule);
		rule = ruleService.addRule(rule);
		Mockito.when(ruleRepository.findAll()).thenReturn(ruleList);
		List<RuleName> ruleListResultat = ruleService.getAllRules();
		assertEquals(ruleListResultat, ruleList);
	}

	@Test
	public void addRuleTest() {
		Mockito.when(ruleRepository.save(rule)).thenReturn(rule);
		RuleName ruleResultat = ruleService.addRule(rule);
		assertEquals(rule, ruleResultat);
	}

	@Test
	public void updateRuleTest() {
		rule.setName("Rule Name Update");
		Mockito.when(ruleRepository.save(rule)).thenReturn(rule);
		RuleName ruleResultat = ruleService.addRule(rule);
		assertEquals(rule, ruleResultat);
	}

	@Test
	public void findRuleByIdTest() {
		Integer id= rule.getId();
		Optional<RuleName> ruleOptional=ruleService.findById(id);
		Optional<RuleName> ruleResult=ruleRepository.findById(id);
		Mockito.when(ruleRepository.findById(id)).thenReturn(ruleOptional);
		assertEquals(ruleResult, ruleOptional);
	}
	
	@Test
	public void deleteRuleTest() {
		boolean ruleResult = ruleService.deleteRule(rule);
		assertTrue(ruleResult);
	}
}
