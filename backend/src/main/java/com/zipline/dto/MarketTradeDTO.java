package com.zipline.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import groovy.transform.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigInteger tradeId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long creatorUserId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String creatorWalletAddress;

    @NotEmpty(message = "NFT ID must not be empty")
    private BigInteger nftId;

    @NotEmpty(message = "Price must not be empty")
    private BigInteger weiPrice;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isOpen;
}
