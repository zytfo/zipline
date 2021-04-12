package com.zipline.smartcontract;

import java.math.BigInteger;

/**
 * Data class, representing one NFT trade
 */
public class Trade {
    public String creatorAddress;
    public BigInteger nftId;
    public BigInteger price;
    public boolean isOpen;

    Trade(String creatorAddress, BigInteger nftId, BigInteger price, boolean isOpen) {
        this.creatorAddress = creatorAddress;
        this.nftId = nftId;
        this.price = price;
        this.isOpen = isOpen;
    }
}
