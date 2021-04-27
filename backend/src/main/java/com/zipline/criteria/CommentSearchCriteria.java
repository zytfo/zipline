package com.zipline.criteria;

import com.zipline.model.PostType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * The type Comment search criteria.
 */
@Getter
@Setter
public class CommentSearchCriteria {
    private String author;
    private Long postId;
    private PostType postType = PostType.NEWS;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created;
}
