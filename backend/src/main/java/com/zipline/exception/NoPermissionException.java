package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No permission exception.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoPermissionException extends RuntimeException {
    private static final long serialVersionUID = 3800037987130852673L;

    /**
     * Instantiates a new No permission exception.
     *
     * @param username          the username
     * @param performedUsername the performed username
     * @param action            the action
     */
    public NoPermissionException(final String username,
                                 final String performedUsername,
                                 final String action) {
        super("User " + username + " cannot " + action + " of the user " + performedUsername);
    }

}
