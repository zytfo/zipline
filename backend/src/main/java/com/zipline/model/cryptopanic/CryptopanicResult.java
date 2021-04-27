package com.zipline.model.cryptopanic;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Cryptopanic result.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptopanicResult {
    @JsonAlias("title")
    private String title;

    @JsonAlias("slug")
    private String description;

    @JsonAlias("published_at")
    private LocalDateTime publishedAt;

    @JsonAlias("source")
    private CryptopanicSource source;

    @JsonAlias("currencies")
    private List<CryptopanicCurrency> currencies;

    @JsonAlias("url")
    private String url;
}
