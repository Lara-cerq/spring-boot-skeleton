package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.validation.Valid;

@Controller
public class CurveController {
	// TODO: Inject Curve Point service
	@Autowired
	CurvePointService curveService;

	@RequestMapping("/curvePoint/list")
	public String home(Model model) {
		// TODO: find all Curve Point, add to model
		List<CurvePoint> curveList = curveService.getAllCurvePoints();
		model.addAttribute("curveList", curveList);
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint curvePoint) {
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		// TODO: check data valid and save to db, after saving return Curve list
		if (!result.hasErrors()) {
			curveService.addCurvePoint(curvePoint);

			List<CurvePoint> curveList = curveService.getAllCurvePoints();
			model.addAttribute("curveList", curveList);
			return "redirect:/curvePoint/list";
		}

		return "curvePoint/add";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// TODO: get CurvePoint by Id and to model then show to the form
		CurvePoint curve = curveService.findById(id);
		model.addAttribute("curve", curve);
		return "curvePoint/update";
	}

	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) {
		// TODO: check required fields, if valid call service to update Curve and return
		if (result.hasErrors()) {
			return "curvePoint/update";
		}
		curvePoint.setId(id);
		curveService.addCurvePoint(curvePoint);
		// Curve list
		List<CurvePoint> curveList = curveService.getAllCurvePoints();
		model.addAttribute("curveList", curveList);
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		// TODO: Find Curve by Id and delete the Curve, return to Curve list
		CurvePoint curve = curveService.findById(id);
		curveService.deleteCurvePoint(curve);

		List<CurvePoint> curveList = curveService.getAllCurvePoints();
		model.addAttribute("curveList", curveList);
		return "redirect:/curvePoint/list";
	}
}
