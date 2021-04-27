package com.zipline.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zipline.model.Status;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * The type Error response.
 */
@XmlRootElement(name = "error")
@Getter
@Setter
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = -4275598516863375430L;

    @JsonIgnore
    private List<String> details;
    private final Status status;

    /**
     * Instantiates a new Error response.
     *
     * @param status the status
     */
    public ErrorResponse(final Status status) {
        super();
        this.status = status;
    }
}
