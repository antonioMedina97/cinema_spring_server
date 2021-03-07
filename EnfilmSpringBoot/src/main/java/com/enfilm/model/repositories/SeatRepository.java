package com.enfilm.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.enfilm.model.entities.Seat;

public interface SeatRepository extends CrudRepository<Seat, Integer> {
	@Query(value = "select * from seats where id = ?", nativeQuery = true)
	public Seat findById(int id);
	
}
