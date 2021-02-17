package com.enfilm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import model.Ticket;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

}
