package com.zipline.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long publicationId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdBy;

    @NotEmpty(message = "Content must not be empty")
    private String content;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer likesCount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> tickers;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean selfLiked;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer numberOfComments;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime created;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updated;
}
