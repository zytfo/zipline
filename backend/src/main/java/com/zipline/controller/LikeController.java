package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsImpl;
import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.LikeDTO;
import com.zipline.dto.PublicationDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.exception.NoPermissionException;
import com.zipline.model.Like;
import com.zipline.model.PostType;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The like controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/like")
@Tag(name = "likes-controller", description = "Likes API")
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);
    private final LikeService likeService;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final UtilService utilService;

    /**
     * Instantiates a new Like controller.
     *
     * @param likeService        the like service
     * @param modelMapper        the model mapper
     * @param userDetailsService the user details service
     * @param utilService        the util service
     */
    @Autowired
    public LikeController(final LikeService likeService,
                          final ModelMapper modelMapper,
                          final UserDetailsServiceImpl userDetailsService,
                          final UtilService utilService) {
        this.likeService = likeService;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.utilService = utilService;
    }

    /**
     * Like post response entity.
     *
     * @param postType the post type
     * @param postId   the post id
     * @return the response entity
     */
    @Operation(summary = "Like/unlike post", description = "Like/unlike post by post type (news, publication, comment) and post id by moderator or admin", tags = {"likes-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post has been liked/unliked",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = LikeDTO.class)))),
            @ApiResponse(responseCode = "406", description = "There is a like for such type of post and post id",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No post for such post type",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No such type of the post",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping("/{postType}/{postId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> likePost(final @ApiParam(value = "Type of post to like/unlike") @PathVariable(value = "postType") PostType postType,
                                      final @ApiParam(value = "Id of the post to like/unlike") @PathVariable(value = "postId") Long postId) {
        logger.debug("REST request to like or unlike post");
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        LikeDTO likeDTO;
        if (!likeService.checkIfLikeExists(postType, postId, userDetails.getId())) {
            final Like like = new Like();
            like.setDate(LocalDateTime.now());
            like.setPostId(postId);
            like.setPostType(postType);
            like.setUserId(userDetails.getId());
            likeDTO = modelMapper.map(likeService.save(like), LikeDTO.class);
            likeDTO.setUsername(userDetailsService.loadUserByUserId(like.getUserId()).getUsername());
            return new ResponseEntity<>(utilService.getResponseBody(likeDTO), HttpStatus.CREATED);
        } else {
            likeService.deleteByPostTypeAndPostIdAndUserId(postType, postId, userDetails.getId());
            return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
        }
    }

    /**
     * Unlike post response entity.
     *
     * @param likeId the like id
     * @return the response entity
     */
    @ApiIgnore
    @Operation(summary = "Unlike post", description = "Unlike post by post like id", tags = {"likes-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful delete",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = LikeDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No such like",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "403", description = "No permission to unlike the post",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @DeleteMapping("/{likeId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> unlikePost(final @ApiParam(value = "Like id to unlike") @PathVariable(value = "likeId") Long likeId) {
        logger.debug("REST request to unlike post");
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final Like like = likeService.getLikeById(likeId);
        if (!like.getUserId().equals(userDetails.getId())) {
            throw new NoPermissionException(userDetails.getUsername(), userDetailsService.loadUserByUserId(like.getUserId()).getUsername(), "unlike the post");
        }
        likeService.deleteByLikeId(likeId);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }

    /**
     * Gets likes for post by post type and post id.
     *
     * @param postType the post type
     * @param postId   the post id
     * @return the likes for post
     */
    @Operation(summary = "Find all likes for certain post type", description = "Get all likes for post of a certain type by user, moderator or admin", tags = {"likes-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PublicationDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No such type of the post",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping("/{postType}/{postId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getLikesForPost(final @ApiParam(value = "Type of post to like") @PathVariable(value = "postType") PostType postType,
                                             final @ApiParam(value = "Id of the post to like") @PathVariable(value = "postId") Long postId) {
        logger.debug("REST request to get all likes by post type and post id");
        final List<LikeDTO> likeDTOs = new ArrayList<>();
        LikeDTO likeDTO;
        for (Like like : likeService.findByPostTypeAndPostId(postType, postId)) {
            likeDTO = modelMapper.map(like, LikeDTO.class);
            likeDTO.setUsername(userDetailsService.loadUserByUserId(like.getUserId()).getUsername());
            likeDTOs.add(likeDTO);
        }
        return new ResponseEntity<>(utilService.getResponseBody(likeDTOs), HttpStatus.OK);
    }
}
