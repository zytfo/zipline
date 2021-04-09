package com.zipline.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity Wallet
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long walletId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    /**
     * It's not nice of course to store key and salt together with the data in DB, but will work for now
     * TODO: invent a better scheme
     */
    @Column(name = "sk", nullable = false)
    private String secretKey;

    @Column(name = "ss", nullable = false)
    private String secretSalt;

    @Column(name = "sv", nullable = false)
    private String secretValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "user_wallets",
            joinColumns = @JoinColumn(name = "wallet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wallet")
    private Set<NFT> nfts = new HashSet<>();

    public Wallet(String name, String address, String secretKey, String secretSalt, String secretValue, User owner) {
        this.name = name;
        this.address = address;
        this.secretKey = secretKey;
        this.secretSalt = secretSalt;
        this.secretValue = secretValue;
        this.owner = owner;
    }
}
