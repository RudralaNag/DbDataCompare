package com.americanexpress.dbcompare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.americanexpress.dbcompare.service.DataComparisonService;

@RestController
public class DataComparisonController {

	@Autowired
	private  DataComparisonService dataComparisonService;
//
//	public DataComparisonController(DataComparisonService dataComparisonService) {
//		this.dataComparisonService = dataComparisonService;
//	}

	@GetMapping("/compare")
	public String compareData() {
		dataComparisonService.compareDatabases();
		return "Data comparison completed. Check logs for details.";
	}
}
