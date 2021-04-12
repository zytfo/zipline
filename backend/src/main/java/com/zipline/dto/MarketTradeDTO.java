package com.zipline.dto;

import groovy.transform.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * DTO object representing a single trade in a marketplace
 */
@Getter
@Setter
@EqualsAndHashCode
public class MarketTradeDTO implements Serializable {
    private static final long serialVersionUID = 9171916707982456622L;

    @Null(message = "Trade ID is assigned automatically")
    private BigInteger tradeId;

    @Null(message = "Creator ID is assigned automatically")
    private Long creatorUserId;

    @Null(message = "Creator address is assigned automatically")
    private String creatorWalletAddress;

    @NotEmpty(message = "NFT ID must not be empty")
    private BigInteger nftId;

    @NotEmpty(message = "Price must not be empty")
    private BigInteger weiPrice;

    @Null(message = "Status is assigned automatically")
    private boolean isOpen;
}
