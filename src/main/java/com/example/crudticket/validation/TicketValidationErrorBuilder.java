package com.example.crudticket.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * Factory class that easily creates a TicketValidationError
 * instance with all the necessary information.
 */
public class TicketValidationErrorBuilder {

    public static TicketValidationError fromBindingErrors(Errors errors) {
        TicketValidationError error = new TicketValidationError("Validation failed. "
                + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
