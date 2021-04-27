package com.zipline.service;

import com.zipline.exception.NoSuchCommentException;
import com.zipline.model.Comment;
import com.zipline.page.CommentPage;
import com.zipline.criteria.CommentSearchCriteria;
import com.zipline.model.PostType;
import com.zipline.repository.CommentCriteriaRepository;
import com.zipline.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Comment service.
 */
@Service
@Transactional
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final CommentCriteriaRepository commentCriteriaRepository;

    /**
     * Instantiates a new Comment service.
     *
     * @param commentRepository         the comment repository
     * @param commentCriteriaRepository the comment criteria repository
     */
    @Autowired
    public CommentService(final CommentRepository commentRepository,
                          final CommentCriteriaRepository commentCriteriaRepository) {
        this.commentRepository = commentRepository;
        this.commentCriteriaRepository = commentCriteriaRepository;
    }

    /**
     * Save comment.
     *
     * @param comment the comment
     * @return the comment
     */
    public Comment save(final Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * Delete by comment id.
     *
     * @param commentId the comment id
     */
    public void deleteByCommentId(final Long commentId) {
        commentRepository.deleteById(commentId);
    }

    /**
     * Gets comment by id.
     *
     * @param commentId the comment id
     * @return the comment by id
     */
    public Comment getCommentById(final Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.orElseThrow(
                () -> new NoSuchCommentException(commentId)
        );
    }

    /**
     * Find all comments by post type and post id.
     *
     * @param postType the post type
     * @param postId   the post id
     * @return the list of comments
     */
    public List<Comment> findByPostTypeAndPostId(final PostType postType, final Long postId) {
        return new ArrayList<>(commentRepository.findAllByPostTypeAndPostId(postType, postId));
    }

    /**
     * Gets number of comments for post.
     *
     * @param postType the post type
     * @param postId   the post id
     * @return the number of comments for post
     */
    public Integer getNumberOfCommentsForPost(final PostType postType, final Long postId) {
        return commentRepository.countByPostTypeAndPostId(postType, postId);
    }

    /**
     * Find all comments by user id.
     *
     * @param userId the user id
     * @return the list of comments
     */
    public List<Comment> findByUserId(final Long userId) {
        return new ArrayList<>(commentRepository.findAllByUserId(userId));
    }

    /**
     * Gets comments.
     *
     * @param commentPage           the comment page
     * @param commentSearchCriteria the comment search criteria
     * @return the comments
     */
    public Page<Comment> getComments(final CommentPage commentPage, final CommentSearchCriteria commentSearchCriteria) {
        return commentCriteriaRepository.findALlWithFilters(commentPage, commentSearchCriteria);
    }
}
