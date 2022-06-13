package com.nnk.springboot.service;

import com.nnk.springboot.Application;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointServiceTests {

	@InjectMocks
	private CurvePointService curvePointService;

	@Mock
	private CurvePointRepository curvePointRepository;

	CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);

	@Test
	public void getAllCurvePointsTest() {
		List<CurvePoint> curvePointList = new ArrayList<>();
		curvePointList.add(curvePoint);
		curvePoint = curvePointService.addCurvePoint(curvePoint);
		Mockito.when(curvePointRepository.findAll()).thenReturn(curvePointList);
		List<CurvePoint> curvePointListResultat = curvePointService.getAllCurvePoints();
		assertEquals(curvePointListResultat, curvePointList);
	}

	@Test
	public void saveCurvePointTest() {
		Mockito.when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
		CurvePoint curvePointresult = curvePointService.addCurvePoint(curvePoint);
		assertEquals(curvePointresult, curvePoint);

	}

	@Test
	public void updateCurvePointTest() {
		curvePoint.setCurveId(20);
		Mockito.when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
		CurvePoint curvePointResult = curvePointService.addCurvePoint(curvePoint);
		assertEquals(curvePointResult, curvePoint);
	}

	@Test
	public void deleteCurvePointTest() {
		boolean curvePointresultat = curvePointService.deleteCurvePoint(curvePoint);
		assertTrue(curvePointresultat);
	}

}
