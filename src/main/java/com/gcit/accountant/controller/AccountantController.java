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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestController
@RequestMapping("/accountant/")
public class AccountantController {
	
	final static Logger logger = LogManager.getLogger(AccountantController.class);
	
	@Autowired
	private ReportService reportService;
	

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleBadRequest(Exception e) {
	    return "invalid request";
	}
	
	//Handle all uncaught exceptions
	@ExceptionHandler(Exception.class)
	public String handleServerError(Exception e) {
		logger.error("sending server error", e);
	    return "server error";
	}
	
	@GetMapping("health")
	public HttpStatus getHealth() {
		return HttpStatus.OK;
	}
	

	//Generate a revenue and tax summary from the given time period
	@GetMapping(value="summaries/{start}/{end}",
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Summary getSummary(
			@PathVariable("start") Date start,
			@PathVariable("end") Date end) {
	
		Summary summary = reportService.getSummary(start, end);
		
		return summary;
	} 
	
	//Generate a product report from the given time period
	@GetMapping(value="categories/{categoryName}/reports/{start}/{end}",
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<ProductReport> getCategoryReport(
			@PathVariable("categoryName") String categoryName,
			@PathVariable("start") Date start,
			@PathVariable("end") Date end) {
	
		List<ProductReport> reports = reportService.getCategoryReport(categoryName, start, end);
		
		return reports;
	} 
	

	
	@PostMapping(value="payments",
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public void addPayment(@RequestBody Payment payment) {
		logger.info("Made payment: " + payment.getAmount());
	}
}