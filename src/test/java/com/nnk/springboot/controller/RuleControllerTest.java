package com.nnk.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RuleControllerTest {

	@MockBean
	RuleService ruleService;

	@Autowired
	MockMvc mockMvc;

	RuleName rule = new RuleName(1, "Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

	@Test
	public void getAllRulesTest() throws Exception {
		List<RuleName> ruleList = new ArrayList<>();
		ruleList.add(rule);
		Mockito.when(ruleService.getAllRules()).thenReturn(ruleList);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/ruleName/list")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("ruleList", "name"))
				.andExpect(MockMvcResultMatchers.view().name("ruleName/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addRuleFormTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/ruleName/add")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("ruleName/add")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addRuleTest() throws Exception {
		Mockito.when(ruleService.addRule(rule)).thenReturn(rule);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/ruleName/validate")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		Integer id = rule.getId();
		rule.setName("test");
		Mockito.when(ruleService.findById(id)).thenReturn(Optional.of(rule));
		Mockito.when(ruleService.addRule(rule)).thenReturn(rule);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/ruleName/update/{id}", id)

				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		;
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.view().name("ruleName/update"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("ruleName"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void updateTradeTest() throws Exception {
		Integer id = rule.getId();
		rule.setName("test");
		Mockito.when(ruleService.findById(id)).thenReturn(Optional.of(rule));
		Mockito.when(ruleService.addRule(rule)).thenReturn(rule);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/ruleName/update/{id}", id)

				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void deleteRuleTest() throws Exception {
		Integer id = rule.getId();
		Mockito.when(ruleService.findById(id)).thenReturn(Optional.of(rule));
		Mockito.when(ruleService.deleteRule(rule)).thenReturn(true);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/ruleName/delete/{id}", id)
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/ruleName/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}
}
