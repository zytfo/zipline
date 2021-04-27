package com.zipline.auth.payload.request;

import com.zipline.model.RoleType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * The Signup request.
 */
@Getter
@Setter
@EqualsAndHashCode
public class SignUpRequestDTO implements Serializable {
    private static final long serialVersionUID = -7937012628548005597L;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private Set<RoleType> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
