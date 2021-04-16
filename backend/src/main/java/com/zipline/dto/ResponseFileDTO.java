package com.zipline.dto;

import lombok.*;

import java.io.Serializable;

/**
 * The type Response file dto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ResponseFileDTO implements Serializable {
    private static final long serialVersionUID = -1485309761680027465L;
    private String name;
    private String url;
    private String type;
    private Integer size;
}
