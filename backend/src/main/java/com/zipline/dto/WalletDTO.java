package com.zipline.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * The Wallet DTO, returned by get or create operations
 */
@Getter
@Setter
@EqualsAndHashCode
public class WalletDTO implements Serializable {
    private static final long serialVersionUID = -83794729886554L;
    private long walletId;
    private String name;
    private String address;
    private Set<BigInteger> nftIds = new HashSet<>();
}
