package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such opened trade exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchOpenedTradeException extends RuntimeException {
    private static final long serialVersionUID = -1477684083152289504L;

    /**
     * Instantiates a new No such opened trade exception.
     *
     * @param tradeId the trade id
     */
    public NoSuchOpenedTradeException(final Long tradeId) {
        super("Opened trade " + tradeId + " not found");
    }
}
