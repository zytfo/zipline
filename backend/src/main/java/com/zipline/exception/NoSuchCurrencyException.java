package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such currency exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchCurrencyException extends RuntimeException {
    private static final long serialVersionUID = -567198780275001164L;

    /**
     * Instantiates a new No such currency exception.
     *
     * @param ticker the ticker
     */
    public NoSuchCurrencyException(final String ticker) {
        super("Currency with ticker " + ticker + " not found");
    }
}
