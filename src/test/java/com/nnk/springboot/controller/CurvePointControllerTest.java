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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CurvePointControllerTest {

	@MockBean
	private CurvePointService curvePointService;

	@Autowired
	MockMvc mockMvc;

	CurvePoint curvePoint = new CurvePoint(1, 10, 10d, 30d);

	@Test
	public void getAllCurvesTest() throws Exception {
		List<CurvePoint> curves = new ArrayList<>();
		curves.add(curvePoint);
		curvePointService.addCurvePoint(curvePoint);
		Mockito.when(curvePointService.getAllCurvePoints()).thenReturn(curves);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/curvePoint/list")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("curveList", "name"))
				.andExpect(MockMvcResultMatchers.view().name("curvePoint/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addCurveFormTest() throws Exception {
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/curvePoint/add")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("curvePoint/add")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void addCurveTest() throws Exception {
		Mockito.when(curvePointService.addCurvePoint(curvePoint)).thenReturn(curvePoint);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/curvePoint/validate")
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("curvePoint/add")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

	@Test
	public void showUpdateFormTest() throws Exception {
		Integer id = curvePoint.getCurveId();
		curvePoint.setValue(20d);
		Mockito.when(curvePointService.findById(id)).thenReturn(Optional.of(curvePoint));
		Mockito.when(curvePointService.addCurvePoint(curvePoint)).thenReturn(curvePoint);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/curvePoint/update/{id}", id)

				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.view().name("curvePoint/update"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void updateCurveTest() throws Exception {
		Integer id = curvePoint.getId();
		curvePoint.setValue(20d);
		Mockito.when(curvePointService.findById(id)).thenReturn(Optional.of(curvePoint));
		Mockito.when(curvePointService.addCurvePoint(curvePoint)).thenReturn(curvePoint);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/curvePoint/update/{id}", id)

				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void deleteCurveTest() throws Exception {
		Integer id = curvePoint.getId();
		Mockito.when(curvePointService.findById(id)).thenReturn(Optional.of(curvePoint));
		Mockito.when(curvePointService.deleteCurvePoint(curvePoint)).thenReturn(true);
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/curvePoint/delete/{id}", id)
				.with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"));
		this.mockMvc.perform(mockRequest).andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list")).andDo(MockMvcResultHandlers.print())
				.andReturn();
	}

}
