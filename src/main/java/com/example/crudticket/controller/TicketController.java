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
 *
 * @author Victor
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class TicketController {

    private TicketRepository ticketRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * GET all Ticket records
     * E.g:
     * Method:  GET
     * Endpoint:
     * http://localhost:8080/api/ticket
     *
     * Response:
     * @returns all tickets stored in the database
     * [
     *     {
     *         "id": "2c9587996e128c83016e128cd1820000",
     *         "description": "ticket number 1",
     *         "created": "2019-10-28T09:27:28.898",
     *         "modified": "2019-10-28T09:27:28.898",
     *         "completed": false
     *     },
     *     {
     *         "id": "2c9587996e128c83016e128ce8380001",
     *         "description": "ticket number 2",
     *         "created": "2019-10-28T09:27:34.712",
     *         "modified": "2019-10-28T09:27:34.712",
     *         "completed": false
     *     }
     * ]
     */
    @GetMapping("/ticket")
    public ResponseEntity<Iterable<Ticket>> getTickets(){
        return ResponseEntity.ok(ticketRepository.findAll());
    }

    /**
     * GET a Ticket record according id
     * E.g:
     * Method:  GET
     * Endpoint:
     * http://localhost:8080/api/ticket/2c9587996e128c83016e128cd1820000
     *
     * Response:
     * @returns the tickets that matches with the id
     *  {
     *    "id": "2c9587996e128c83016e128cd1820000",
     *    "description": "ticket number 1",
     *    "created": "2019-10-28T09:27:28.898",
     *    "modified": "2019-10-28T09:27:28.898",
     *    "completed": false
     *  }
     */
    @GetMapping("/ticket/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String id){
        Optional<Ticket> ticket =
                ticketRepository.findById(id);
        if(ticket.isPresent())
            return ResponseEntity.ok(ticket.get());

        return ResponseEntity.notFound().build();
    }

    /**
     * Patch a Ticket record by updating its completed status to true
     * E.g:
     * Method:  PATCH
     * Endpoint:
     * http://localhost:8080/api/ticket/2c9587996e128c83016e128cd1820000
     *
     * Response: None
     * HTTP Status Code: 200
     */
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

    /**
     * (1)Creates a Ticket record by updating its completed status to true
     * E.g:
     * Method:  POST
     * Endpoint:
     * http://localhost:8080/api/ticket/
     *  {
     *    "description": "ticket number 1 edited by a certain reason"
     *  }
     *
     * Response:
     *  {
     *    "id": "2c9587996e128c83016e128cd1820000",
     *    "description": "ticket number 1",
     *    "created": "2019-10-28T09:27:28.898",
     *    "modified": "2019-10-28T09:27:28.898",
     *    "completed": false
     *  }
     *
     * ------------------------------------------------------------------
     * (2)Updates a Ticket record when the id is sent in the request body
     * E.g:
     * Method:  PUT
     * Endpoint:
     * http://localhost:8080/api/ticket/
     *
     *  {
     *     "id": "2c9587996e128c83016e128cd1820000",
     *    "description": "ticket number 1 edited by a certain reason"
     *  }
     *
     * Response:
     * @returns the ticket updated
     *  {
     *    "id": "2c9587996e128c83016e128cd1820000",
     *    "description": "ticket number 1 edited by a certain reason",
     *    "created": "2019-10-28T09:27:28.898",
     *    "modified": "2019-10-28T09:27:28.898",
     *    "completed": false
     *  }
     *
     */
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

    /**
     * DELETES a Ticket record according id
     * E.g:
     * Method:  DELETE
     * Endpoint:
     * http://localhost:8080/api/ticket/2c9587996e128c83016e128cd1820000
     *
     * Response: None
     * HTTP Status Code: 204  No Content
     *
     */
    @DeleteMapping("/ticket/{id}")
    public ResponseEntity<Ticket> deleteTicket(@PathVariable String id){
        ticketRepository.delete(TicketBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETES all ticket records from the database
     * E.g:
     * Method:  DELETE
     * Endpoint:
     * http://localhost:8080/api/ticket/
     *
     * Response: None
     * HTTP Status Code: 204  No Content
     *
     */
    @DeleteMapping("/ticket")
    public ResponseEntity<Ticket> deleteTicket(@RequestBody Ticket ticket){
        ticketRepository.delete(ticket);
        return ResponseEntity.noContent().build();
    }

    /**
     * All validations are controlled in this method
     *
     * E.g: Trying create a ticket with an empty description
     * Method:  POST
     * Endpoint:
     * http://localhost:8080/api/ticket/
     *  {
     *    "description": ""
     *  }
     *
     * HTTP status Code: 400 Bad Request
     * Response:
     *  {
     *      "errors": [
     *          "must not be blank"
     *      ],
     *      "errorMessage": "Validation failed. 1 error(s)"
     *  }
     *
     *
     */
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public TicketValidationError handleException(Exception exception) {
        return new TicketValidationError(exception.getMessage());
    }
}
