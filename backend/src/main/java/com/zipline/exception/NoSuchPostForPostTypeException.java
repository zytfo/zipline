package com.zipline.exception;

import com.zipline.model.PostType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type No such post for post type exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchPostForPostTypeException extends RuntimeException {
    private static final long serialVersionUID = -803146473664094286L;

    /**
     * Instantiates a new No such post for post type exception.
     *
     * @param postType the post type
     * @param postId   the post id
     */
    public NoSuchPostForPostTypeException(final PostType postType, final Long postId) {
        super("Post " + postId + " with " + postType.name() + " type not found");
    }
}
