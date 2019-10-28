package com.example.crudticket.controller;

import com.example.crudticket.model.Ticket;
import com.example.crudticket.model.TicketBuilder;
import com.example.crudticket.repository.TicketRepository;
import com.example.crudticket.validation.TicketValidationError;
import com.example.crudticket.validation.TicketValidationErrorBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;


/**
 * TicketController class to see all the Spring MVC features, the annotations,
 * the way to configure endpoints, and how to handle errors.
 */
@RestController
@RequestMapping("/api")
public class TicketController {

    private TicketRepository ticketRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/ticket")
    public ResponseEntity<Iterable<Ticket>> getTickets(){
        return ResponseEntity.ok(ticketRepository.findAll());
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String id){
        Optional<Ticket> ticket =
                ticketRepository.findById(id);
        if(ticket.isPresent())
            return ResponseEntity.ok(ticket.get());

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/ticket/{id}")
    public ResponseEntity<Ticket> setCompleted(@PathVariable String id){
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if(!ticket.isPresent())
            return ResponseEntity.notFound().build();

        Ticket result = ticket.get();
        result.setCompleted(true);
        ticketRepository.save(result);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.ok().header("Location",location.toString()).build();
    }

    @RequestMapping(value="/ticket", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<?> createTicket(@Valid @RequestBody Ticket ticket, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(TicketValidationErrorBuilder.fromBindingErrors(errors));
        }

        Ticket result = ticketRepository.save(ticket);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(result);
    }

    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable String id){
        ticketRepository.delete(TicketBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/ticket")
    public ResponseEntity<Ticket> deleteTicket(@RequestBody Ticket ticket){
        ticketRepository.delete(ticket);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public TicketValidationError handleException(Exception exception) {
        return new TicketValidationError(exception.getMessage());
    }

}
