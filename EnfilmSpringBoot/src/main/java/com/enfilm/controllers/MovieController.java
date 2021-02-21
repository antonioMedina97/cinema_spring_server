package com.enfilm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enfilm.model.entities.Movie;
import com.enfilm.model.repositories.MovieRepository;

@CrossOrigin
@RestController
public class MovieController {

	@Autowired MovieRepository movieRep;
	
	@GetMapping("/movie/all")
	public List<Movie> findAllMovies() {
		return (List<Movie>) this.movieRep.findAll();
	}
	
	
	
}
