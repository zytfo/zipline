package com.zipline.model.cryptopanic;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Cryptopanic source.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptopanicSource {
    @JsonAlias("title")
    private String title;

    @JsonAlias("region")
    private String region;

    @JsonAlias("domain")
    private String domain;
}
