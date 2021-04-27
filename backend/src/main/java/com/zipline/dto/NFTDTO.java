package com.zipline.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * The NFT DTO.
 */
@Getter
@Setter
@EqualsAndHashCode
public class NFTDTO implements Serializable {
    private static final long serialVersionUID = -837871729886554L;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigInteger nftId;

    @NotEmpty(message = "Wallet ID must not be empty")
    private Long walletId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String walletAddress;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Description must not be empty")
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imageUrl;

    private String externalLink;
}
