package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsImpl;
import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.ComplaintDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.exception.NoPermissionException;
import com.zipline.model.Complaint;
import com.zipline.model.PostType;
import com.zipline.service.CommentService;
import com.zipline.service.ComplaintService;
import com.zipline.service.PublicationService;
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

import java.time.LocalDateTime;

/**
 * The complaint controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/complaint")
@Tag(name = "complaints-controller", description = "Complaints API")
public class ComplaintController {
    private static final Logger logger = LoggerFactory.getLogger(ComplaintController.class);
    private final ComplaintService complaintService;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final UtilService utilService;
    private final PublicationService publicationService;
    private final CommentService commentService;

    /**
     * Instantiates a new Complaint controller.
     *
     * @param complaintService   the complaint service
     * @param modelMapper        the model mapper
     * @param userDetailsService the user details service
     * @param utilService        the util service
     * @param publicationService the publication service
     * @param commentService     the comment service
     */
    @Autowired
    public ComplaintController(
            final ComplaintService complaintService,
            final ModelMapper modelMapper,
            final UserDetailsServiceImpl userDetailsService,
            final UtilService utilService,
            final PublicationService publicationService,
            final CommentService commentService) {
        this.complaintService = complaintService;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.utilService = utilService;
        this.publicationService = publicationService;
        this.commentService = commentService;
    }

    /**
     * Gets complaint.
     *
     * @param complaintId the complaint id
     * @return the complaint
     */
    @Operation(summary = "Get complaint by id", description = "Get complaint by complaint id for user, moderator or admin", tags = {"complaints-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Complaint has been loaded",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ComplaintDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Complaint not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping(value = "/{complaintId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getComplaint(final @ApiParam(value = "Complaint id to find a complaint") @PathVariable("complaintId") Long complaintId) {
        logger.debug("REST request to get a complaint {}", complaintId);
        return new ResponseEntity<>(utilService.getResponseBody(modelMapper.map(complaintService.getComplaintById(complaintId), ComplaintDTO.class)), HttpStatus.OK);
    }

    /**
     * Create complaint response entity.
     *
     * @param complaintDTO the complaint dto
     * @return the response entity
     */
    @Operation(summary = "Create complaint", description = "Create complaint for moderator, user or admin", tags = {"complaints-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Complaint has been created",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ComplaintDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No such type of the post",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No post for such post type",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping(value = "/create", consumes = "text/plain")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> createComplaint(
            final @ApiParam(value = "Complaint body") @RequestBody ComplaintDTO complaintDTO) {
        logger.debug("REST request to create a complaint for post type {} and post {}", complaintDTO.getPostType(), complaintDTO.getPostId());
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final PostType type = utilService.getPostTypeByString(complaintDTO.getPostType().toString());
        switch (type) {
            case PUBLICATION:
                complaintDTO.setPostAuthorId(publicationService.getPublicationById(complaintDTO.getPostId()).getCreatedBy());
                break;
            case COMMENT:
                complaintDTO.setPostAuthorId(commentService.getCommentById(complaintDTO.getPostId()).getUserId());
        }
        utilService.checkPostTypeAndPostId(type, complaintDTO.getPostId());
        complaintDTO.setUserId(userDetails.getId());
        complaintDTO.setCreated(LocalDateTime.now());
        final Complaint complaint = complaintService.save(modelMapper.map(complaintDTO, Complaint.class));
        return new ResponseEntity<>(utilService.getResponseBody(modelMapper.map(complaint, ComplaintDTO.class)), HttpStatus.CREATED);
    }

    /**
     * Delete complaint by id response entity.
     *
     * @param complaintId the complaint id
     * @return the response entity
     */
    @Operation(summary = "Delete complaint", description = "Delete complaint by id for moderator or admin", tags = {"complaints-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful delete",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ComplaintDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "403", description = "No permission to delete complaint",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No such complaint",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @DeleteMapping(value = "/delete/{complaintId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> deleteComplaintById(
            final @ApiParam(value = "Complaint id to delete") @PathVariable(value = "complaintId") Long complaintId) {
        logger.debug("REST request to delete complaint by complaint id {}", complaintId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final Complaint loadedComplaint = complaintService.getComplaintById(complaintId);
        if (!loadedComplaint.getUserId().equals(userDetails.getId())) {
            throw new NoPermissionException(userDetails.getUsername(), userDetailsService.loadUserByUserId(loadedComplaint.getUserId()).getUsername(), "delete the complaint");
        }
        complaintService.deleteByComplaintId(complaintId);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }

    /**
     * Gets complaints by author id.
     *
     * @param postAuthorId the post author id
     * @return the complaints by author id
     */
    @Operation(summary = "Get complaints by post author id", description = "Get complaints by post author id for user, moderator or admin", tags = {"complaints-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Complaints have been loaded",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ComplaintDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Post author id not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping(value = "/postAuthor/{postAuthorId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getComplaintsByAuthorId(final @ApiParam(value = "Post author id to find complaints") @PathVariable("postAuthorId") Long postAuthorId) {
        logger.debug("REST request to get complaints by post author {}", postAuthorId);
        userDetailsService.loadUserByUserId(postAuthorId);
        return new ResponseEntity<>(utilService.getResponseBody(modelMapper.map(complaintService.findByPostAuthorId(postAuthorId), ComplaintDTO.class)), HttpStatus.OK);
    }
}
