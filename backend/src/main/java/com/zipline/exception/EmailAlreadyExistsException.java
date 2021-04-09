package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Email already exists exception.
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EmailAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 8166583984367500083L;

    /**
     * Instantiates a new Email already exists exception.
     *
     * @param email the email
     */
    public EmailAlreadyExistsException(final String email) {
        super("Email " + email + " is already in use");
    }
}
