package com.gcit.accountant.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Component
@Aspect
public class RequestLoggingAspect {
	
	final static Logger logger = LogManager.getLogger(RequestLoggingAspect.class);
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void httpGet() { }
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void httpPost() { }
	
	@Before("httpGet()") 
	public void logGetRequest(JoinPoint joinPoint) {
		
		String requestName = getRequestName(joinPoint);
		logger.info("Get: " + requestName);
	}
	
	@Before("httpPost()")
	public void logPostRequest(JoinPoint joinPoint) {
		
		String requestName = getRequestName(joinPoint);
		logger.info("Post: " + requestName);
	}
	
    private String getRequestName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }
}
