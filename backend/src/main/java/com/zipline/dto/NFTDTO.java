package com.zipline.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * The NFT DTO.
 */
@Getter
@Setter
@EqualsAndHashCode
public class NFTDTO implements Serializable {
    private static final long serialVersionUID = -837871729886554L;

    private Long nftId;

    @NotEmpty(message = "Wallet ID must not be empty")
    private Long walletId;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Description must not be empty")
    private String description;

    @NotEmpty(message = "Image URL must not be empty")
    private String imageUrl;

    private String externalLink;
}
