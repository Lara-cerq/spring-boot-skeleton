package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

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

	public RuleName addRule(RuleName rule) {
		return ruleRepository.save(rule);
	}

	public Optional<RuleName> findById(Integer id) {
		return ruleRepository.findById(id);
	}

	public boolean deleteRule(RuleName rule) {
		ruleRepository.delete(rule);
		return true;
	}

}
