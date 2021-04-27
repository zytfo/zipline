package com.zipline.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * The News DTO.
 */
@Getter
@Setter
@EqualsAndHashCode
public class NewsDTO implements Serializable {
    private static final long serialVersionUID = -5033583749614843463L;
    private Long newsId;

    @JsonIgnore
    private Long createdBy;
    private String title;
    private String content;
    private String description;
    private String imageUrl;
    private String source;
    private Integer likesCount;
    private List<String> tags;
    private Boolean selfLiked;
    private Integer numberOfComments;
    private LocalDateTime created;
}
