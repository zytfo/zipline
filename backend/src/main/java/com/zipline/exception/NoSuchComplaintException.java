package com.zipline.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such complaint exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchComplaintException extends RuntimeException {
    private static final long serialVersionUID = 3053092725553050962L;

    /**
     * Instantiates a new No such complaint exception.
     *
     * @param complaintId the complaint id
     */
    public NoSuchComplaintException(final Long complaintId) {
        super("Complaint " + complaintId + " not found");
    }
}
