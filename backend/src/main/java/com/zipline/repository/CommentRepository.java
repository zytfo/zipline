package com.zipline.repository;

import com.zipline.model.Comment;
import com.zipline.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The comment repository contains methods for comment entities.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Find all comments by post type and post id.
     *
     * @param postType the post type
     * @param postId   the post id
     * @return the list of comments
     */
    List<Comment> findAllByPostTypeAndPostId(@Param("postType") PostType postType, @Param("postId") Long postId);

    /**
     * Find all comments by user id.
     *
     * @param userId the user id
     * @return the list of comments
     */
    List<Comment> findAllByUserId(@Param("userId") Long userId);

    /**
     * Count by post type and post id integer.
     *
     * @param postType the post type
     * @param postId   the post id
     * @return the integer
     */
    Integer countByPostTypeAndPostId(@Param("postType") PostType postType, @Param("postId") Long postId);
}
