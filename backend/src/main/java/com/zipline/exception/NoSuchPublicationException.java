package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such publication exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchPublicationException extends RuntimeException {
    private static final long serialVersionUID = 6110802851469628092L;

    /**
     * Instantiates a new No such publication exception.
     *
     * @param publicationId the publication id
     */
    public NoSuchPublicationException(final Long publicationId) {
        super("Publication " + publicationId + " not found");
    }
}
