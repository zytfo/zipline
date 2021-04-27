package com.zipline.auth.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * The Jwt response.
 */
@Getter
@Setter
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = 7008542450034041630L;
    private final List<String> roles;
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    /**
     * Instantiates a new Jwt response.
     *
     * @param accessToken the access token
     * @param id          the id
     * @param username    the username
     * @param email       the email
     * @param roles       the roles
     */
    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
