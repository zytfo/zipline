package com.zipline.dto;

import com.zipline.model.PostType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The Comment DTO.
 */
@Getter
@Setter
@EqualsAndHashCode
public class CommentDTO implements Serializable {
    private static final long serialVersionUID = -7482353273837313114L;
    private Long commentId;
    private String author;

    @NotEmpty(message = "Post type must not be empty")
    private PostType postType;
    private Long postId;

    @NotEmpty(message = "Message must not be empty")
    private String message;
    private Integer likesCount;
    private List<String> tickers;
    private Boolean selfLiked;
    private LocalDateTime created;
    private LocalDateTime updated;
}
