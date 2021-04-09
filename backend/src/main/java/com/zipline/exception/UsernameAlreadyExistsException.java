package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Username already exists exception.
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UsernameAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = -630718539624428103L;

    /**
     * Instantiates a new Username already exists exception.
     *
     * @param username the username
     */
    public UsernameAlreadyExistsException(final String username) {
        super("Username " + username + " is already in use");
    }
}
