package com.example.crudticket.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Ticket domain model class, which has all required fields.
 * @Data annotation is a Lombok annotation that generates a default constructor
 * and add  all the setters, getters, and overrides, such as the toString
 * method, to make the class cleaner.
 * To use JPA and be compliant, it is necessary to declare the entity (@Entity) and the
 * primary key (@Id) from the domain model.
 * @NoArgsConstructor. This annotation belongs to the Lombok library.
 * It creates a class constructor with no arguments. It is required that
 * JPA have a constructor with no arguments.
 */
@Entity
@Data
@NoArgsConstructor
public class Ticket {

    /**
     * In this class the ID and description fields are marked as @NotNull.
     * The description field has an extra @NotBlank annotation to make sure
     * that it is never empty.
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @NotNull
    @NotBlank
    private String description;
    // @Column. This annotation specifies the mapped column for persistent properties;
    // marking the created field to be only for inserts but never for updates.
    @Column(insertable = true, updatable = false)
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;

    /**
     * Constructor  which only requires description parameter to create a
     * Ticket instance.
     * @param description String to describe the ticket details.
     */
    public Ticket(String description){
        this();
        this.description = description;
    }

    /**
     * @PrePersist. This annotation is a callback that is triggered before any
     * persistent action is taken.
     * In this case this method sets the new timestamp for the created
     * and modified fields before the record is inserted into the database.
     */
    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    /**
     * @PreUpdate. This annotation is another callback that is triggered
     * before any update action is taken.
     * In this case this method sets the new timestamp for the
     * modified field before it is updated into the database.
     */
    @PreUpdate
    void onUpdate() {
        this.setModified(LocalDateTime.now());
    }
}
