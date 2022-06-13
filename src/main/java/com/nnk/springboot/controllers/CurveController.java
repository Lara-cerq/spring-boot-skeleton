package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

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
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class CurveController {

	private static Logger logger = LoggerFactory.getLogger(CurveController.class);

	@Autowired
	CurvePointService curveService;

	@RequestMapping("/curvePoint/list")
	public String home(Model model, Principal principal) {
		// find all Curve Point
		List<CurvePoint> curveList = curveService.getAllCurvePoints();
		model.addAttribute("curveList", curveList);
		// get name of user
		model.addAttribute("name", principal.getName());
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint curvePoint) {
		return "curvePoint/add";
	}

	@Transactional
	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		// check data valid and save to db
		if (!result.hasErrors()) {
			curveService.addCurvePoint(curvePoint);
			logger.info("NEW CURVE ADDED");
			List<CurvePoint> curveList = curveService.getAllCurvePoints();
			model.addAttribute("curveList", curveList);
			// return Curve list
			return "redirect:/curvePoint/list";
		}
		return "curvePoint/add";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// get CurvePoint by Id
		CurvePoint curve = curveService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid curve point Id:" + id));
		model.addAttribute("curvePoint", curve);
		return "curvePoint/update";
	}

	@Transactional
	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) {
		// check required fields
		if (result.hasErrors()) {
			return "curvePoint/update";
		}
		curvePoint.setId(id);
		// call service to update Curve
		curveService.addCurvePoint(curvePoint);
		logger.info("CURVE UPDATED");
		// Curve list
		List<CurvePoint> curveList = curveService.getAllCurvePoints();
		model.addAttribute("curveList", curveList);
		// return Curve list
		return "redirect:/curvePoint/list";
	}

	@Transactional
	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		// Find Curve by Id
		Optional<CurvePoint> curve = curveService.findById(id);
		// delete the Curve
		curveService.deleteCurvePoint(curve.get());
		logger.info("CURVE DELETED");
		List<CurvePoint> curveList = curveService.getAllCurvePoints();
		model.addAttribute("curveList", curveList);
		// return to Curve list
		return "redirect:/curvePoint/list";
	}
}
