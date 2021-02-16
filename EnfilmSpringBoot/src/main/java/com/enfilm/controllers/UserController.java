package com.enfilm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/usuario")
	public String doGet() {
		return "get";
	}
	
	@PostMapping("/usuario")
	public String doPost() {
		return "Post";
	}
	
	public UserController() {
		// TODO Auto-generated constructor stub
	}

}
