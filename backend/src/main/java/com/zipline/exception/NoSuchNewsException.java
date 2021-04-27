package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such news exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchNewsException extends RuntimeException {
    private static final long serialVersionUID = 6727732793228106723L;

    /**
     * Instantiates a new No such news exception.
     *
     * @param newsId the news id
     */
    public NoSuchNewsException(final Long newsId) {
        super("News " + newsId + " not found");
    }
}
