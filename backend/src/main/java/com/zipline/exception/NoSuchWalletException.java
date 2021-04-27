package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such wallet exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchWalletException extends RuntimeException {
    private static final long serialVersionUID = 611080268069628092L;

    /**
     * Instantiates a new No such walletID exception.
     *
     * @param walletID the wallet id
     */
    public NoSuchWalletException(final Long walletID) {
        super("Wallet " + walletID + " not found");
    }
}
