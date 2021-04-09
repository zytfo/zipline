package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such post type exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchPostTypeException extends RuntimeException {
    private static final long serialVersionUID = 1763894296799540421L;

    /**
     * Instantiates a new No such post type exception.
     *
     * @param postType the post type
     */
    public NoSuchPostTypeException(final String postType) {
        super("Post type " + postType + " not found");
    }
}
