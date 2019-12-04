package com.gcit.accountant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.accountant.model.LoginRequest;
import com.gcit.accountant.model.LoginResponse;
import com.gcit.accountant.model.UserPrincipal;
import com.gcit.accountant.security.JwtTokenProvider;

@RestController
@RequestMapping("/")
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
    public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
    	
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
      

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        String jwt = tokenProvider.generateToken(principal);
        return new LoginResponse(jwt);
    }
}
