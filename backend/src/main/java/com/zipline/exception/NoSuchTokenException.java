package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such token exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchTokenException extends RuntimeException {
    private static final long serialVersionUID = -5898538512938139426L;

    /**
     * Instantiates a new No such token exception.
     *
     * @param token the token
     */
    public NoSuchTokenException(final String token) {
        super("Token " + token + " not found");
    }
}
