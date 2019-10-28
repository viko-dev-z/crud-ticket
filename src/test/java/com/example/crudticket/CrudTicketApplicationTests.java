package com.example.crudticket;

import com.example.crudticket.model.Ticket;
import com.example.crudticket.repository.TicketRepository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test suite for basic API actions
 *
 * @author Victor
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@DataJpaTest
class CrudTicketApplicationTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TicketRepository repository;

    /**
     * Unit test for reviewing the creation of a Ticket object using JPA Hibernate
     */
    @Test
    public void toDoDataTest() throws Exception {
        this.entityManager.persist(new Ticket("Ticket created for unit test purposes"));
        Iterable<Ticket> tickets = this.repository.findByDescriptionContains("Ticket created for unit test purposes");
        assertThat(tickets.iterator().next()).toString().contains("Ticket created for unit test purposes");
    }
}
