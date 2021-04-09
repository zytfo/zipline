package com.zipline.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Status.
 */
@Getter
@Setter
public class Status implements Serializable {
    private static final long serialVersionUID = 5096600640341273049L;
    private final LocalDateTime timestamp;
    private final Integer code;
    private final String message;

    /**
     * Instantiates a new Status.
     *
     * @param code    the code
     * @param message the error message
     */
    public Status(final Integer code, final String message) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.message = message;
    }

    /**
     * Gets success status.
     *
     * @return the success status
     */
    public static Status getSuccessStatus() {
        return new Status(0, "");
    }

    /**
     * Gets too many requests status.
     *
     * @return the too many requests status
     */
    public static Status getTooManyRequestsStatus() {
        return new Status(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests");
    }
}
