package com.zipline.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * The entity NFT.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "nfts")
public class NFT {
    @Id
    @Column(name = "id", nullable = false)
    private BigInteger nftId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "external_link")
    private String externalLink;

    @Column(name = "image_url", nullable = false, unique = true)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "wallet_nfts",
            joinColumns = @JoinColumn(name = "nft_id"),
            inverseJoinColumns = @JoinColumn(name = "wallet_id"))
    private Wallet wallet;
}
