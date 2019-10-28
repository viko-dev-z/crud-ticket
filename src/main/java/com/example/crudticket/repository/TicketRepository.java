package com.example.crudticket.repository;

import com.example.crudticket.model.Ticket;
import org.springframework.data.repository.CrudRepository;


/**
 * One of the most important benefits from the Spring Data JPA is that we don’t need
 * to worry about implementing basic CRUD functionalities, because that’s what it
 */
public interface TicketRepository extends CrudRepository<Ticket,String> {

}

