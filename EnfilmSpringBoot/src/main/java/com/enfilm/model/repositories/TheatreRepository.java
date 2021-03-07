package com.enfilm.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.enfilm.model.entities.Theatre;

public interface TheatreRepository extends CrudRepository<Theatre, Integer> {
	
	@Query(value = "select * from theatres where id = ?", nativeQuery = true)
	public Theatre findById(int id);
	
	
}
