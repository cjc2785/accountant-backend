package com.gcit.accountant.controller;

import com.gcit.accountant.model.*;

import java.sql.Date;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/accountant/")
public class AccountantController {

	//Generate a report from the given time period
	@GetMapping(value="reports/{start}/{end}",
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Report getReport(
			@PathVariable("start") Date start,
			@PathVariable("end") Date end) {
	
			return null;
	} 
}