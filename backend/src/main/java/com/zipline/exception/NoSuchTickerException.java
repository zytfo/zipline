package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such ticker exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchTickerException extends RuntimeException {
    private static final long serialVersionUID = 8778830659982358798L;

    /**
     * Instantiates a new No such ticker exception.
     *
     * @param ticker the ticker
     */
    public NoSuchTickerException(final String ticker) {
        super("Ticker " + ticker + " not found");
    }
}
