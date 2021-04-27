package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such role exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchRoleException extends RuntimeException {
    private static final long serialVersionUID = 8880756307255405495L;

    /**
     * Instantiates a new No such role exception.
     *
     * @param roleType the role type
     */
    public NoSuchRoleException(final String roleType) {
        super("Role type " + roleType + " not found");
    }
}
