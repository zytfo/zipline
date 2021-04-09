package com.zipline.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * The WalletImportExportDTO for importing and exporting wallets
 */
@Getter
@Setter
@EqualsAndHashCode
public class WalletImportExportDTO implements Serializable {
    private static final long serialVersionUID = -83711129886554L;
    private long walletId;

    @NotEmpty(message = "Wallet name must not be empty")
    private String walletName;

    @NotEmpty(message = "Private key must not be empty")
    private String privateKey;
}
