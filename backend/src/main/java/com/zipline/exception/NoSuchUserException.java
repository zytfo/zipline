package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such user exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchUserException extends RuntimeException {
    private static final long serialVersionUID = 741043090414734128L;

    /**
     * Instantiates a new No such user exception.
     *
     * @param userId the user id
     */
    public NoSuchUserException(final Long userId) {
        super("User " + userId + " not found");
    }
}
