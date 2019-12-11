package com.gcit.accountant.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.accountant.model.LoginRequest;
import com.gcit.accountant.model.UserPrincipal;
import com.gcit.accountant.security.JwtTokenProvider;

@RestController
@CrossOrigin(origins="http://accountant-frontend.s3-website-us-east-1.amazonaws.com", allowCredentials="true")
@RequestMapping("/api/")
public class RootController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    
    
	@GetMapping("health")
	public HttpStatus getHealth() {
		return HttpStatus.OK;
	}

    @PostMapping(value="login",
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public HttpStatus authenticateUser(@RequestBody LoginRequest loginRequest, 
    			HttpServletResponse response) {
    	
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
      

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        String jwt = tokenProvider.generateToken(principal);
        response.addCookie(new Cookie("session-id", jwt));
        return HttpStatus.OK;
    }
}
