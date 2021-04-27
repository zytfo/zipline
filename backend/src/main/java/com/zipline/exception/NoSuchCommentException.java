package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such comment exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchCommentException extends RuntimeException {
    private static final long serialVersionUID = -51502977541961633L;

    /**
     * Instantiates a new No such comment exception.
     *
     * @param commentId the comment id
     */
    public NoSuchCommentException(final Long commentId) {
        super("Comment " + commentId + " not found");
    }

}
