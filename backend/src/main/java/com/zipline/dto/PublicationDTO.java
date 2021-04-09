package com.zipline.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * The Publication DTO.
 */
@Getter
@Setter
@EqualsAndHashCode
public class PublicationDTO implements Serializable {
    private static final long serialVersionUID = -8376077173299886554L;
    private Long publicationId;
    private String createdBy;
    private String content;
    private Integer likesCount;
    private List<String> tickers;
    private Boolean selfLiked;
    private Integer numberOfComments;
    private LocalDateTime created;
    private LocalDateTime updated;
}
