package com.enfilm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enfilm.repositories.UserRepository;

@RestController
public class UserController {

	UserRepository UserRep;

}
