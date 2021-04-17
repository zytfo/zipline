package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigInteger;

/**
 * The type No such trade exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchTradeException extends RuntimeException {
    private static final long serialVersionUID = 74103090414734128L;

    /**
     * Instantiates a new No such trade exception.
     *
     * @param tradeId the trade id
     */
    public NoSuchTradeException(final BigInteger tradeId) {
        super("Trade " + tradeId + " not found");
    }
}
