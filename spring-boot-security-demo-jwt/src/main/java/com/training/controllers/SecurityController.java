package com.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.training.model.UserInput;
import com.training.util.JwtUtil;

@RestController
public class SecurityController {
	
	
	@GetMapping("/admin/hello")
	public String sayHelloAdmin()
	{
		return "This is a Sample message for admin";
	}
	
	@GetMapping("/user/hello")
	public String sayHelloUser()
	{
		return "This is a Sample message for user";
	}
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/authenticate")
	public String authenticate(@RequestBody UserInput input)
	{
		try { 
			authManager.authenticate(new
					UsernamePasswordAuthenticationToken(input.getUsername(),input.getPassword()));

			// load the userdetailsservice 
			// validate the user
			// User Object
			return jwtUtil.generateToken(input.getUsername()); } 

		catch(BadCredentialsException ex) {

			return ex.getMessage(); } 
	}
}








