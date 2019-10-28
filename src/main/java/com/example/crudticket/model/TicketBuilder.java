package com.example.crudticket.model;

/**
 * TicketBuilder Fluent API class that helps create a Ticket instance.
 * This class is a factory that creates a Ticket with a description
 * or with a particular ID.
 */
public class TicketBuilder {

    private static TicketBuilder instance = new TicketBuilder();
    private String id = null;
    private String description = "";
    private TicketBuilder(){}

    public static TicketBuilder create() {
        return instance;
    }

    public TicketBuilder withDescription(String description){
        this.description = description;
        return instance;
    }

    public TicketBuilder withId(String id){
        this.id = id;
        return instance;
    }

    public Ticket build(){
        Ticket result = new Ticket(this.description);
        if(id != null)
            result.setId(id);
        return result;
    }
}
