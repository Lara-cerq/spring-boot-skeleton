package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleService {

	@Autowired
	RuleNameRepository ruleRepository;

	public List<RuleName> getAllRules() {
		return ruleRepository.findAll();
	}

	public void addRule(RuleName rule) {
		ruleRepository.save(rule);
	}

	public RuleName findById(Integer id) {
		return ruleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
	}

	public void deleteRule(RuleName rule) {
		ruleRepository.delete(rule);
	}

}
