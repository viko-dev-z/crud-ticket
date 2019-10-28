package com.example.crudticket.validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation class that exposes any possible errors in the app, such as a
 * Ticket with no description.
 * The class holds any errors that arise with any requests.
 * It uses an extra @JsonInclude annotation, which says that even if the
 * errors field is empty, it must be included.
 */
public class TicketValidationError {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors = new ArrayList<>();
    private final String errorMessage;

    public TicketValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public void addValidationError(String error) {
        errors.add(error);
    }
    public List<String> getErrors() {
        return errors;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
