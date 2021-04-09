package com.zipline.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * The Message response.
 */
@Getter
@Setter
@AllArgsConstructor
public class MessageResponse implements Serializable {
    private static final long serialVersionUID = 1744021550874278339L;
    private String message;
}
