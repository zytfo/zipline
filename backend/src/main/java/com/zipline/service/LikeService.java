package com.zipline.service;

import com.zipline.exception.LikeAlreadyExistsException;
import com.zipline.exception.NoSuchLikeException;
import com.zipline.model.Like;
import com.zipline.model.PostType;
import com.zipline.repository.LikeRepository;
import com.zipline.repository.NewsRepository;
import com.zipline.repository.PublicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Like service.
 */
@Service
@Transactional
public class LikeService {
    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);
    private final LikeRepository likeRepository;
    private final NewsRepository newsRepository;
    private final PublicationRepository publicationRepository;

    /**
     * Instantiates a new Like service.
     *
     * @param likeRepository        the like repository
     * @param newsRepository        the news repository
     * @param publicationRepository the publication repository
     */
    @Autowired
    public LikeService(final LikeRepository likeRepository,
                       final NewsRepository newsRepository,
                       final PublicationRepository publicationRepository) {
        this.likeRepository = likeRepository;
        this.newsRepository = newsRepository;
        this.publicationRepository = publicationRepository;
    }

    /**
     * Save like.
     *
     * @param like the like entity
     * @return the like
     */
    public Like save(final Like like) {
        return likeRepository.save(like);
    }

    /**
     * Gets like by id.
     *
     * @param likeId the like id
     * @return the like by id
     */
    public Like getLikeById(final Long likeId) {
        Optional<Like> like = likeRepository.findById(likeId);
        return like.orElseThrow(
                () -> new NoSuchLikeException(likeId)
        );
    }

    /**
     * Delete by like id.
     *
     * @param likeId the like id
     */
    public void deleteByLikeId(final Long likeId) {
        getLikeById(likeId);
        likeRepository.deleteById(likeId);
    }

    /**
     * Find all likes by post type and post id.
     *
     * @param postType the post type
     * @param postId   the post id
     * @return the list of likes
     */
    public List<Like> findByPostTypeAndPostId(final PostType postType, final Long postId) {
        return new ArrayList<>(likeRepository.findAllByPostTypeAndPostId(postType, postId));
    }

    /**
     * Check if like already exists.
     *
     * @param postType the post type
     * @param postId   the post id
     * @param userId   the user id
     */
    public void checkIfLikeAlreadyExists(final PostType postType, final Long postId, final Long userId) {
        final Optional<Like> like = likeRepository.findLikeByPostTypeAndPostIdAndUserId(postType, postId, userId);
        if (like.isPresent()) {
            throw new LikeAlreadyExistsException(postType, postId, userId);
        }
    }

    /**
     * Gets number of likes by post type and post id.
     *
     * @param postType the post type
     * @param postId   the post id
     * @return the number of likes by post type and post id
     */
    public Integer getNumberOfLikesByPostTypeAndPostId(final PostType postType, final Long postId) {
        return likeRepository.countByPostTypeAndPostId(postType, postId);
    }

    /**
     * Check if like exists boolean.
     *
     * @param postType the post type
     * @param postId   the post id
     * @param userId   the user id
     * @return the boolean
     */
    public Boolean checkIfLikeExists(final PostType postType, final Long postId, final Long userId) {
        return likeRepository.existsByPostTypeAndPostIdAndUserId(postType, postId, userId);
    }

    /**
     * Delete by post type and post id and user id.
     *
     * @param postType the post type
     * @param postId   the post id
     * @param userId   the user id
     */
    public void deleteByPostTypeAndPostIdAndUserId(final PostType postType, final Long postId, final Long userId) {
        likeRepository.deleteByPostTypeAndPostIdAndUserId(postType, postId, userId);
    }
}
