package com.enfilm.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.enfilm.model.entities.Movie;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

	
	

	// Mensajes recibidos
	@Query(value = "SELECT * from movies", nativeQuery = true)
	public List<Movie> getAllMovies();
}
