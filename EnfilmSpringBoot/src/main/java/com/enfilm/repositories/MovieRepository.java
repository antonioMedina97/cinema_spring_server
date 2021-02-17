package com.enfilm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {

}
