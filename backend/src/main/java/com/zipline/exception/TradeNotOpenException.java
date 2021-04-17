package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigInteger;

/**
 * The type No such trade exception.
 */
@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class TradeNotOpenException extends RuntimeException {
    private static final long serialVersionUID = 74101090414734128L;

    /**
     * Instantiates a new TradeNotOpenException.
     *
     * @param tradeId the trade id
     */
    public TradeNotOpenException(final BigInteger tradeId) {
        super("Trade " + tradeId + " is not open");
    }
}
