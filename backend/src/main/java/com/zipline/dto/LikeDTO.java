package com.zipline.dto;

import com.zipline.model.PostType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Like DTO.
 */
@Getter
@Setter
@EqualsAndHashCode
public class LikeDTO implements Serializable {
    private static final long serialVersionUID = 9171916703752456622L;
    private Long likeId;

    @NotEmpty(message = "Username must not be empty")
    private String username;

    @NotEmpty(message = "Post type must not be empty")
    private PostType postType;

    @NotEmpty(message = "Post type must not be empty")
    private Long postId;
    private LocalDateTime date;
}
