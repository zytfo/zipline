package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such like exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchLikeException extends RuntimeException {
    private static final long serialVersionUID = -2198124416675788497L;

    /**
     * Instantiates a new No such like exception.
     *
     * @param likeId the like id
     */
    public NoSuchLikeException(final Long likeId) {
        super("Like " + likeId + " not found");
    }
}
