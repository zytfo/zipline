package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such NFT exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchNFTException extends RuntimeException {
    /**
     * Instantiates a new No such NFT exception.
     *
     * @param nftId the NFT id
     */
    public NoSuchNFTException(final Long nftId) {
        super("NFT " + nftId + " not found");
    }
}
