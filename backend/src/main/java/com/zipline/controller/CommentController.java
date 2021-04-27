package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsImpl;
import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.criteria.CommentSearchCriteria;
import com.zipline.dto.CommentDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.exception.NoPermissionException;
import com.zipline.model.*;
import com.zipline.page.CommentPage;
import com.zipline.service.CommentService;
import com.zipline.service.LikeService;
import com.zipline.service.UtilService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The comment controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
@Tag(name = "comments-controller", description = "Comments API")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final UtilService utilService;
    private final LikeService likeService;

    /**
     * Instantiates a new Comment controller.
     *
     * @param commentService     the comment service
     * @param modelMapper        the model mapper
     * @param userDetailsService the user details service
     * @param utilService        the util service
     * @param likeService        the like service
     */
    @Autowired
    public CommentController(final CommentService commentService,
                             final ModelMapper modelMapper,
                             final UserDetailsServiceImpl userDetailsService,
                             final UtilService utilService,
                             final LikeService likeService) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.utilService = utilService;
        this.likeService = likeService;
    }

    /**
     * Gets comments.
     *
     * @param commentPage           the comment page
     * @param commentSearchCriteria the comment search criteria
     * @return the comments
     */
    @Operation(summary = "Load comments", description = "Get comments for user, moderator or admin", tags = {"comments-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments have been loaded",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class))))})
    @GetMapping(value = "/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getComments(
            final @ApiParam(value = "Comment page parameters") CommentPage commentPage,
            final @ApiParam(value = "Comment search criteria parameters") CommentSearchCriteria commentSearchCriteria) {
        logger.debug("REST request to get comments");
        final List<CommentDTO> commentDTOs = new ArrayList<>();
        final Page<Comment> pageComment = commentService.getComments(commentPage, commentSearchCriteria);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final List<Comment> commentList = pageComment.getContent();
        CommentDTO commentDTO;
        for (Comment comment : commentList) {
            commentDTO = modelMapper.map(comment, CommentDTO.class);
            commentDTO.setAuthor(userDetailsService.loadUserByUserId(comment.getUserId()).getUsername());
            commentDTO.setSelfLiked(likeService.checkIfLikeExists(PostType.COMMENT, comment.getCommentId(), userDetails.getId()));
            commentDTO.setLikesCount(likeService.getNumberOfLikesByPostTypeAndPostId(PostType.COMMENT, comment.getCommentId()));
            commentDTO.setTickers(utilService.extractTickers(comment.getMessage()));
            commentDTOs.add(commentDTO);
        }
        return new ResponseEntity<>(utilService.getResponseBody(commentDTOs), HttpStatus.OK);
    }

    /**
     * Gets comment by id.
     *
     * @param commentId the comment id
     * @return the comment
     */
    @Operation(summary = "Get comment by id", description = "Get comment by comment id for user, moderator or admin", tags = {"comments-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment has been loaded",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Comment not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping(value = "/{commentId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getComment(final @ApiParam(value = "Comment id to find a comment") @PathVariable("commentId") Long commentId) {
        logger.debug("REST request to get a comment {}", commentId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        CommentDTO commentDTO = modelMapper.map(commentService.getCommentById(commentId), CommentDTO.class);
        commentDTO.setAuthor(commentDTO.getAuthor());
        commentDTO.setSelfLiked(likeService.checkIfLikeExists(PostType.COMMENT, commentId, userDetails.getId()));
        commentDTO.setLikesCount(likeService.getNumberOfLikesByPostTypeAndPostId(PostType.COMMENT, commentId));
        commentDTO.setTickers(utilService.extractTickers(commentDTO.getMessage()));
        return new ResponseEntity<>(utilService.getResponseBody(commentDTO), HttpStatus.OK);
    }

    /**
     * Create comment response entity.
     *
     * @param postType the post type
     * @param postId   the post id
     * @param message  the message
     * @return the response entity
     */
    @Operation(summary = "Create comment", description = "Create comment for moderator, user or admin", tags = {"comments-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment has been created",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No such type of the post",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No post for such post type",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping(value = "/create/{postType}/{postId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> createComment(
            final @ApiParam(value = "Type of the post") @PathVariable(value = "postType") PostType postType,
            final @ApiParam(value = "Post id to create a comment") @PathVariable(value = "postId") Long postId,
            final @ApiParam(value = "The message of a comment") @RequestParam("message") String message) {
        logger.debug("REST request to create a comment for post type {} and post {}", postType, postId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        utilService.checkPostTypeAndPostId(postType, postId);
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userDetails.getId());
        comment.setCreated(LocalDateTime.now());
        comment.setPostType(postType);
        comment.setMessage(message);
        comment = commentService.save(comment);
        final CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        commentDTO.setTickers(utilService.extractTickers(commentDTO.getMessage()));
        commentDTO.setAuthor(userDetails.getUsername());
        return new ResponseEntity<>(utilService.getResponseBody(commentDTO), HttpStatus.CREATED);
    }

    /**
     * Update comment response entity.
     *
     * @param message   the message
     * @param commentId the comment id
     * @return the response entity
     */
    @Operation(summary = "Update comment", description = "Update comment by id for moderator, admin or user", tags = {"comments-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful update",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "403", description = "No permission to update the post",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No such comment",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PutMapping(value = "/{commentId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> updateComment(
            final @ApiParam(value = "Comment message to update") @RequestParam("message") String message,
            final @ApiParam(value = "Comment id to update") @PathVariable(value = "commentId") Long commentId) {
        logger.debug("REST request to update comment by comment id {}", commentId);
        Comment loadedComment = commentService.getCommentById(commentId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        if (!loadedComment.getUserId().equals(userDetails.getId())) {
            throw new NoPermissionException(userDetails.getUsername(), userDetailsService.loadUserByUserId(loadedComment.getUserId()).getUsername(), "update the comment");
        }
        loadedComment.setMessage(message);
        loadedComment.setUpdated(LocalDateTime.now());
        loadedComment = commentService.save(loadedComment);
        final CommentDTO commentDTO = modelMapper.map(loadedComment, CommentDTO.class);
        commentDTO.setTickers(utilService.extractTickers(commentDTO.getMessage()));
        commentDTO.setAuthor(userDetails.getUsername());
        return new ResponseEntity<>(utilService.getResponseBody(commentDTO), HttpStatus.ACCEPTED);
    }

    /**
     * Delete comment by id response entity.
     *
     * @param commentId the comment id
     * @return the response entity
     */
    @Operation(summary = "Delete comment", description = "Delete comment by id for moderator or admin", tags = {"comments-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful delete",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "403", description = "No permission to delete comment",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No such comment",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @DeleteMapping(value = "/delete/{commentId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> deleteCommentById(
            final @ApiParam(value = "Comment id to delete") @PathVariable(value = "commentId") Long commentId) {
        logger.debug("REST request to delete comment by comment id {}", commentId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final Comment loadedComment = commentService.getCommentById(commentId);
        if (!loadedComment.getUserId().equals(userDetails.getId())) {
            throw new NoPermissionException(userDetails.getUsername(), userDetailsService.loadUserByUserId(loadedComment.getUserId()).getUsername(), "delete the comment");
        }
        commentService.deleteByCommentId(commentId);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }
}
