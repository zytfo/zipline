package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigInteger;

/**
 * The type InsufficientBalanceException.
 */
@ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
public class InsufficientBalanceException extends RuntimeException {
    private static final long serialVersionUID = 74103090411134128L;

    /**
     * Instantiates a new InsufficientBalanceException.
     *
     * @param balance  the balance
     * @param required the required amount
     */
    public InsufficientBalanceException(final BigInteger balance, final BigInteger required) {
        super("Cannot execute operation: having " + balance.toString() + ", but required " + required.toString());
    }
}
