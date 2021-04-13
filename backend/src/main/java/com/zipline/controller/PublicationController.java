package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsImpl;
import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.PublicationDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.exception.NoPermissionException;
import com.zipline.model.PostType;
import com.zipline.model.Publication;
import com.zipline.page.PublicationPage;
import com.zipline.criteria.PublicationSearchCriteria;
import com.zipline.service.*;
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
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Publication controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/publication")
@Tag(name = "publications-controller", description = "Publications API")
public class PublicationController {
    private static final Logger logger = LoggerFactory.getLogger(PublicationController.class);
    private final PublicationService publicationService;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final UtilService utilService;
    private final LikeService likeService;
    private final CommentService commentService;
    private final MarketService marketService;

    /**
     * Instantiates a new Publication controller.
     *
     * @param publicationService the publication service
     * @param modelMapper        the model mapper
     * @param userDetailsService the user details service
     * @param utilService        the util service
     * @param likeService        the like service
     * @param commentService     the comment service
     * @param marketService      the market service
     */
    @Autowired
    public PublicationController(final PublicationService publicationService,
                                 final ModelMapper modelMapper,
                                 final UserDetailsServiceImpl userDetailsService,
                                 final UtilService utilService,
                                 final LikeService likeService,
                                 final CommentService commentService,
                                 final MarketService marketService) {
        this.publicationService = publicationService;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.utilService = utilService;
        this.likeService = likeService;
        this.commentService = commentService;
        this.marketService = marketService;
    }

    /**
     * Gets all publications.
     *
     * @param publicationPage           the publication page
     * @param publicationSearchCriteria the publication search criteria
     * @return the all publications
     */
    @Operation(summary = "Find all publications", description = "Get all publications for user, moderator or admin", tags = {"publications-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PublicationDTO.class))))})
    @GetMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllPublications(
            final @ApiParam(value = "Publications page parameters") PublicationPage publicationPage,
            final @ApiParam(value = "Publications search criteria parameters") PublicationSearchCriteria publicationSearchCriteria) {
        logger.debug("REST request to get all publications");
        final List<PublicationDTO> publicationDTOs = new ArrayList<>();
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final Page<Publication> pagePublications = publicationService.getPublications(publicationPage, publicationSearchCriteria);
        final List<Publication> publicationList = pagePublications.getContent();
        PublicationDTO publicationDTO;
        for (Publication publication : publicationList) {
            publicationDTO = modelMapper.map(publication, PublicationDTO.class);
            publicationDTO.setCreatedBy(userDetailsService.loadUserByUserId(publication.getCreatedBy()).getUsername());
            publicationDTO.setSelfLiked(likeService.checkIfLikeExists(PostType.PUBLICATION, publication.getPublicationId(), userDetails.getId()));
            publicationDTO.setLikesCount(likeService.getNumberOfLikesByPostTypeAndPostId(PostType.PUBLICATION, publication.getPublicationId()));
            if (publicationDTO.getTradeIds() != null)
                publicationDTO.setMarketTradeDTOs(marketService.getTradesByTradeIds(publicationDTO.getTradeIds()));
            publicationDTO.setNumberOfComments(commentService.getNumberOfCommentsForPost(PostType.PUBLICATION, publication.getPublicationId()));
            publicationDTOs.add(publicationDTO);
        }
        return new ResponseEntity<>(utilService.getResponseBody(publicationDTOs), HttpStatus.OK);
    }

    /**
     * Gets publication.
     *
     * @param publicationId the publication id
     * @return the publication
     */
    @Operation(summary = "Get publication by id", description = "Get publication by publication id for user, moderator or admin", tags = {"publications-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication has been loaded",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PublicationDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No such publication",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping(value = "/{publicationId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getPublication(
            final @ApiParam(value = "Publication id to find the publication") @PathVariable("publicationId") Long publicationId) {
        logger.debug("REST request to get publication by id {}", publicationId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final Publication publication = publicationService.getPublicationById(publicationId);
        PublicationDTO publicationDTO = modelMapper.map(publication, PublicationDTO.class);
        publicationDTO.setCreatedBy(userDetailsService.loadUserByUserId(publication.getCreatedBy()).getUsername());
        publicationDTO.setSelfLiked(likeService.checkIfLikeExists(PostType.PUBLICATION, publicationId, userDetails.getId()));
        publicationDTO.setLikesCount(likeService.getNumberOfLikesByPostTypeAndPostId(PostType.PUBLICATION, publicationId));
        if (publicationDTO.getTradeIds() != null)
            publicationDTO.setMarketTradeDTOs(marketService.getTradesByTradeIds(publicationDTO.getTradeIds()));
        publicationDTO.setNumberOfComments(commentService.getNumberOfCommentsForPost(PostType.PUBLICATION, publicationId));
        return new ResponseEntity<>(utilService.getResponseBody(publicationDTO), HttpStatus.OK);
    }

    /**
     * Create publication response entity.
     *
     * @param publicationDTO the publication
     * @return the response entity
     */
    @Operation(summary = "Create publication", description = "Create publication for moderator, user or admin", tags = {"publications-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publication has been created",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PublicationDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> createPublication(
            @ApiParam(value = "The new publication") @RequestBody PublicationDTO publicationDTO) {
        logger.debug("REST request to create a new publication");
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        Publication publication = new Publication();
        publication.setTickers(utilService.extractTickers(publicationDTO.getContent()));
        publication.setContent(publicationDTO.getContent());
        publication.setCreated(LocalDateTime.now());
        publication.setCreatedBy(userDetails.getId());
        publication.setTradeIds(marketService.checkAndGetOpenedTrades(publicationDTO.getTradeIds()));
        if (publicationDTO.getTradeIds() != null)
            publicationDTO.setMarketTradeDTOs(marketService.getTradesByTradeIds(publicationDTO.getTradeIds()));
        publication = publicationService.save(publication);
        publicationDTO = modelMapper.map(publication, PublicationDTO.class);
        publicationDTO.setCreatedBy(userDetails.getUsername());
        return new ResponseEntity<>(utilService.getResponseBody(publicationDTO), HttpStatus.CREATED);
    }

    /**
     * Update publication response entity.
     *
     * @param publicationDTO the publication
     * @param publicationId  the publication id
     * @return the response entity
     */
    @Operation(summary = "Update publication", description = "Update publication by id for moderator, admin or user", tags = {"publications-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful update",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PublicationDTO.class)))),
            @ApiResponse(responseCode = "403", description = "No permission to update the publication",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No such publication",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PutMapping(value = "/{publicationId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> updatePublication(
            @ApiParam(value = "Publication to be updated") @RequestBody PublicationDTO publicationDTO,
            final @ApiParam(value = "Publication id to be updated") @PathVariable(value = "publicationId") Long publicationId) {
        logger.debug("REST request to update the publication");
        final Publication loadedPublication = publicationService.getPublicationById(publicationId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        if (!loadedPublication.getCreatedBy().equals(userDetails.getId())) {
            throw new NoPermissionException(userDetails.getUsername(), userDetailsService.loadUserByUserId(loadedPublication.getCreatedBy()).getUsername(), "update the publication");
        }
        loadedPublication.setContent(publicationDTO.getContent());
        loadedPublication.setTickers(utilService.extractTickers(publicationDTO.getContent()));
        loadedPublication.setUpdated(LocalDateTime.now());
        loadedPublication.setTradeIds(marketService.checkAndGetOpenedTrades(publicationDTO.getTradeIds()));
        if (publicationDTO.getTradeIds() != null)
            publicationDTO.setMarketTradeDTOs(marketService.getTradesByTradeIds(publicationDTO.getTradeIds()));
        final Publication publication = publicationService.save(loadedPublication);
        publicationDTO = modelMapper.map(publication, PublicationDTO.class);
        publicationDTO.setCreatedBy(userDetails.getUsername());
        publicationDTO.setNumberOfComments(commentService.getNumberOfCommentsForPost(PostType.PUBLICATION, publicationId));
        return new ResponseEntity<>(utilService.getResponseBody(publicationDTO), HttpStatus.ACCEPTED);
    }

    /**
     * Delete publication by id response entity.
     *
     * @param publicationId the publication id
     * @return the response entity
     */
    @Operation(summary = "Delete publication", description = "Delete publication by id for moderator or admin", tags = {"publications-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful delete",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PublicationDTO.class)))),
            @ApiResponse(responseCode = "403", description = "No permission to delete the publication",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No such publication",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @DeleteMapping(value = "/delete/{publicationId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> deletePublicationById(
            final @ApiParam(value = "Publication id to be deleted") @PathVariable(value = "publicationId") Long publicationId) {
        logger.debug("REST request to delete the publication by publication id {}", publicationId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final Publication loadedPublication = publicationService.getPublicationById(publicationId);
        if (!loadedPublication.getCreatedBy().equals(userDetails.getId())) {
            throw new NoPermissionException(userDetails.getUsername(), userDetailsService.loadUserByUserId(loadedPublication.getCreatedBy()).getUsername(), "delete the publication");
        }
        publicationService.deleteByPublicationId(publicationId);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }

    /**
     * Gets all publications by ticker.
     *
     * @param ticker the ticker
     * @return all publications by ticker
     */
    @Deprecated
    @ApiIgnore
    @Operation(summary = "Find all publications by ticker", description = "Get all publications by ticker for user, moderator or admin", tags = {"publications-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PublicationDTO.class)))),
            @ApiResponse(responseCode = "404", description = "no such ticker",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping("/search/{ticker}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllPublicationsByTicker(
            final @ApiParam(value = "Ticker to search publications") @PathVariable(value = "ticker") String ticker) {
        logger.debug("REST request to get all publications by ticker {}", ticker);
        utilService.tickerDoesExist(ticker);
        final List<PublicationDTO> publicationDTOs = new ArrayList<>();
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        PublicationDTO publicationDTO;
        for (Publication publication : publicationService.findAllByTicker(ticker)) {
            publicationDTO = modelMapper.map(publication, PublicationDTO.class);
            publicationDTO.setCreatedBy(userDetailsService.loadUserByUserId(publication.getCreatedBy()).getUsername());
            publicationDTO.setSelfLiked(likeService.checkIfLikeExists(PostType.PUBLICATION, publication.getPublicationId(), userDetails.getId()));
            publicationDTO.setLikesCount(likeService.getNumberOfLikesByPostTypeAndPostId(PostType.PUBLICATION, publication.getPublicationId()));
            publicationDTOs.add(publicationDTO);
        }
        return new ResponseEntity<>(utilService.getResponseBody(publicationDTOs), HttpStatus.OK);
    }

    /**
     * Gets all publications by tag.
     *
     * @param tag the tag
     * @return the all publications by tag
     */
    @Deprecated
    @ApiIgnore
    @Operation(summary = "Find all publications by tag", description = "Get all publications by tag for user, moderator or admin", tags = {"publications-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PublicationDTO.class))))})
    @GetMapping("/search/{tag}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllPublicationsByTag(
            final @ApiParam(value = "Tag to search publications") @PathVariable(value = "tag") String tag) {
        logger.debug("REST request to get all publications by tag {}", tag);
        final List<PublicationDTO> publicationDTOs = new ArrayList<>();
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        PublicationDTO publicationDTO;
        for (Publication publication : publicationService.findAllByTag(tag)) {
            publicationDTO = modelMapper.map(publication, PublicationDTO.class);
            publicationDTO.setCreatedBy(userDetailsService.loadUserByUserId(publication.getCreatedBy()).getUsername());
            publicationDTO.setSelfLiked(likeService.checkIfLikeExists(PostType.PUBLICATION, publication.getPublicationId(), userDetails.getId()));
            publicationDTO.setLikesCount(likeService.getNumberOfLikesByPostTypeAndPostId(PostType.PUBLICATION, publication.getPublicationId()));
            publicationDTOs.add(publicationDTO);
        }
        return new ResponseEntity<>(utilService.getResponseBody(publicationDTOs), HttpStatus.OK);
    }
}
