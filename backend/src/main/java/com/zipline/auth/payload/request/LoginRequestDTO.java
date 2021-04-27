package com.zipline.auth.payload.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Login request.
 */
@Getter
@Setter
@EqualsAndHashCode
public class LoginRequestDTO implements Serializable {
    private static final long serialVersionUID = 1574756099183360045L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
