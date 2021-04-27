package com.zipline.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Currency DTO.
 */
@Getter
@Setter
@EqualsAndHashCode
public class CurrencyDTO implements Serializable {
    private static final long serialVersionUID = -4319575964472576852L;

    @JsonIgnore
    private Long currencyId;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotEmpty(message = "Ticker must not be empty")
    private String ticker;

    @NotEmpty(message = "Description must not be empty")
    private String description;
}
