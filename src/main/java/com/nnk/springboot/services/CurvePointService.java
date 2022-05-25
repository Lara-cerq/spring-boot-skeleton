package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {
	
	@Autowired
	CurvePointRepository curvePointRepository;
	
	public List<CurvePoint> getAllCurvePoints() {
		return curvePointRepository.findAll();
	}
	
	public void addCurvePoint(CurvePoint curvePoint) {
		curvePointRepository.save(curvePoint);
	}
	
	public CurvePoint findById(Integer id) {
		return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curve point Id:" + id));
	}
	
	public void deleteCurvePoint(CurvePoint curvePoint) {
		curvePointRepository.delete(curvePoint);
	}

}
