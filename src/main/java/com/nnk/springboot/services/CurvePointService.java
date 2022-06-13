package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

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
	
	public CurvePoint addCurvePoint(CurvePoint curvePoint) {
		return curvePointRepository.save(curvePoint);
	}
	
	public Optional<CurvePoint> findById(Integer id) {
		return curvePointRepository.findById(id);
	}
	
	public boolean deleteCurvePoint(CurvePoint curvePoint) {
		curvePointRepository.delete(curvePoint);
		return true;
	}

}
