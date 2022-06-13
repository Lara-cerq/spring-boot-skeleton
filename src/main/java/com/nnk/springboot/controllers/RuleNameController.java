package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleService;

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
public class RuleNameController {

	private static Logger logger = LoggerFactory.getLogger(RuleNameController.class);

	@Autowired
	RuleService ruleService;

	@RequestMapping("/ruleName/list")
	public String home(Model model, Principal principal) {
		// find all RuleName
		List<RuleName> ruleList = ruleService.getAllRules();
		// add to model
		model.addAttribute("ruleList", ruleList);
		model.addAttribute("name", principal.getName());
		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName bid) {
		return "ruleName/add";
	}

	@Transactional
	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		// check data valid
		if (!result.hasErrors()) {
			// save to db
			ruleService.addRule(ruleName);
			logger.info("RULE NAME ADDED");
			List<RuleName> ruleList = ruleService.getAllRules();
			model.addAttribute("ruleList", ruleList);
			// return RuleName list
			return "redirect:/ruleName/list";
		}
		return "ruleName/add";
	}

	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// get RuleName by Id
		RuleName rule = ruleService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
		// add to model
		model.addAttribute("ruleName", rule);
		// show the form
		return "ruleName/update";
	}

	@Transactional
	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) {
		// check required fields, if valid call service to update RuleName and
		if (result.hasErrors()) {
			return "ruleName/update";
		}
		ruleName.setId(id);
		// call service to update RuleName
		ruleService.addRule(ruleName);
		logger.info("RULE NAME UPDATED");
		List<RuleName> ruleList = ruleService.getAllRules();
		model.addAttribute("ruleList", ruleList);
		// return RuleName list
		return "redirect:/ruleName/list";
	}

	@Transactional
	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		// Find RuleName by Id
		RuleName rule = ruleService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid rule Id:" + id));
		// delete the RuleName
		ruleService.deleteRule(rule);
		logger.info("RULE NAME DELETED");
		List<RuleName> ruleList = ruleService.getAllRules();
		model.addAttribute("ruleList", ruleList);
		// return to Rule list
		return "redirect:/ruleName/list";
	}
}
