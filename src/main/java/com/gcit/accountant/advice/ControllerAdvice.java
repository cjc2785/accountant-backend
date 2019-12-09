package com.gcit.accountant.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestControllerAdvice
public class ControllerAdvice {
	
	final static Logger logger = LogManager.getLogger(ControllerAdvice.class);
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleBadRequest(Exception e) {
	    return "invalid request";
	}
	
	//Handle missing required request parameter
	@ExceptionHandler(MissingServletRequestParameterException.class) 
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleMissingParameter(Exception e) {
		return "invalid request";
	}
}
