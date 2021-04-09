package com.zipline.repository;

import com.zipline.model.Like;
import com.zipline.model.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The like repository contains methods for like entities.
 */
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    /**
     * Find all likes by post type and post id.
     *
     * @param postType the post type
     * @param postId   the post id
     * @return the list of likes
     */
    List<Like> findAllByPostTypeAndPostId(@Param("postType") PostType postType, @Param("postId") Long postId);

    /**
     * Find like by post type, post id and user id.
     *
     * @param postType the post type
     * @param postId   the post id
     * @param userId   the user id
     * @return the like
     */
    Optional<Like> findLikeByPostTypeAndPostIdAndUserId(@Param("postType") PostType postType, @Param("postId") Long postId, @Param("userId") Long userId);

    /**
     * Count by post type and post id integer.
     *
     * @param postType the post type
     * @param postId   the post id
     * @return the integer
     */
    Integer countByPostTypeAndPostId(@Param("postType") PostType postType, @Param("postId") Long postId);

    /**
     * Exists by post type and post id and user id boolean.
     *
     * @param postType the post type
     * @param postId   the post id
     * @param userId   the user id
     * @return the boolean
     */
    Boolean existsByPostTypeAndPostIdAndUserId(@Param("postType") PostType postType, @Param("postId") Long postId, @Param("userId") Long userId);

    /**
     * Delete by post type and post id and user id.
     *
     * @param postType the post type
     * @param postId   the post id
     * @param userId   the user id
     */
    void deleteByPostTypeAndPostIdAndUserId(@Param("postType") PostType postType, @Param("postId") Long postId, @Param("userId") Long userId);
}
