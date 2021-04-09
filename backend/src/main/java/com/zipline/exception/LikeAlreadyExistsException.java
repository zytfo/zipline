package com.zipline.exception;

import com.zipline.model.PostType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Like already exists exception.
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class LikeAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = -2930505171421929308L;

    /**
     * Instantiates a new Like already exists exception.
     *
     * @param postType the post type
     * @param postId   the post id
     * @param userId   the user id
     */
    public LikeAlreadyExistsException(final PostType postType,
                                      final Long postId,
                                      final Long userId) {
        super("There is already a like for post type " + postType.name() + " and post " + postId + " for user " + userId);
    }
}
