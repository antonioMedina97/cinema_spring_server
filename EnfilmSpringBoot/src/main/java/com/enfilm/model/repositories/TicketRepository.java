package com.enfilm.model.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.enfilm.model.entities.Ticket;

@Repository
public interface TicketRepository  extends CrudRepository<Ticket, Integer>{
	
;

	@Query(value = "select * from tickets where id_movie = ?", nativeQuery = true)
	public Ticket findByid_movie(int id_movie);
	
	@Query(value = "select * from tickets where id_user = ?", nativeQuery = true)
	public List<Ticket> findById_User(int id);
	

	@Query(value = "select * from tickets where id_movie = ? and datetime  = ? ", nativeQuery = true)
	public List<Ticket> findByid_movieAndDate(int id_movie, Date datetime);
	
	@Query(value = "select datetime from tickets where id_movie = ? group by datetime", nativeQuery = true)
	public List<Date> getDateSesionesByMovie(int idMovie);
}
