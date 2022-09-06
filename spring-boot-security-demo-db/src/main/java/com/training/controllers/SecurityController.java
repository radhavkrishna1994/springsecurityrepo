package com.training.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
	
	@GetMapping("/hello")
	public String sayHello()
	{
		return "This is a Sample message";
	}

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
}
