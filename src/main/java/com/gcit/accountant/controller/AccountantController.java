package com.gcit.accountant.controller;

import com.gcit.accountant.model.*;
import com.gcit.accountant.service.ReportService;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins="http://accountant-frontend.s3-website-us-east-1.amazonaws.com", allowCredentials="true")
@RequestMapping("/api/accountant/")
public class AccountantController {
	
	final static Logger logger = LogManager.getLogger(AccountantController.class);
	
	@Autowired
	private ReportService reportService;
	
	

	//Generate a revenue and tax summary from the given time period
	@GetMapping(value="summaries",
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Summary getSummary(
			@RequestParam("start") Date start,
			@RequestParam("end") Date end) {
	
		Summary summary = reportService.getSummary(start, end);
		
		return summary;
	} 
	
	//Generate a product report from the given time period
	@GetMapping(value="reports",
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<ProductReport> getCategoryReports(
			@RequestParam("category") String categoryName,
			@RequestParam("start") Date start,
			@RequestParam("end") Date end) {
	
		List<ProductReport> reports = reportService.getCategoryReport(categoryName, start, end);
		
		return reports;
	}
	
	@PostMapping(value="payments",
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	public void addPayment(@RequestBody Payment payment) {
		logger.info("Made payment: " + payment.getAmount());
	}
}