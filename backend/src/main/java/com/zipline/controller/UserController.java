package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsImpl;
import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.NewsDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.model.News;
import com.zipline.model.PostType;
import com.zipline.repository.UserRepository;
import com.zipline.service.LikeService;
import com.zipline.service.NewsFeedService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The type User controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@Tag(name = "user-controller", description = "User API")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final NewsFeedService newsFeedService;
    private final UtilService utilService;
    private final LikeService likeService;

    /**
     * Instantiates a new User controller.
     *
     * @param userRepository     the user repository
     * @param modelMapper        the model mapper
     * @param userDetailsService the user details service
     * @param newsFeedService    the news feed service
     * @param utilService        the util service
     * @param likeService        the like service
     */
    public UserController(
            final UserRepository userRepository,
            final ModelMapper modelMapper,
            final UserDetailsServiceImpl userDetailsService,
            final NewsFeedService newsFeedService,
            final UtilService utilService,
            final LikeService likeService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.newsFeedService = newsFeedService;
        this.utilService = utilService;
        this.likeService = likeService;
    }

    /**
     * Gets current user.
     *
     * @return the user
     */
    @Operation(summary = "Get current user", description = "Get current user", tags = {"user-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDetailsImpl.class)))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping("/")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getCurrentUser() {
        logger.debug("REST request to get current user");
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        return new ResponseEntity<>(utilService.getResponseBody(userDetails), HttpStatus.OK);
    }

    /**
     * Gets user by username.
     *
     * @param username the username
     * @return the user by user name
     */
    @Operation(summary = "Get user by username", description = "Get user by his username", tags = {"user-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDetailsImpl.class)))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getUserByUserName(
            final @ApiParam(value = "Username to be found") @PathVariable(value = "username") String username) {
        logger.debug("REST request to get user {}", username);
        final UserDetailsImpl loadedUser = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        return new ResponseEntity<>(utilService.getResponseBody(loadedUser), HttpStatus.OK);
    }

    /**
     * Mark or unmark news by id response entity.
     *
     * @param newsId the news id
     * @return the response entity
     */
    @Operation(summary = "Mark or unmark news", description = "Mark/unmark news by id by user, admin or moderator", tags = {"user-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful mark/unmark",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No such news",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping(value = "/mark/{newsId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> markNewsById(
            final @ApiParam(value = "News id to be marked/unmarked") @PathVariable(value = "newsId") Long newsId) {
        logger.debug("REST request to mark/unmark news by id {}", newsId);
        final News loadedNews = newsFeedService.getNewsById(newsId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final Set<News> readingList = new HashSet<>(userDetails.getMarkedNews());
        if (readingList.contains(loadedNews)) {
            readingList.remove(loadedNews);
        } else {
            readingList.add(loadedNews);
        }
        userDetailsService.saveMarkedNews(userDetails.getId(), readingList);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }

    /**
     * Gets reading list.
     *
     * @return the reading list
     */
    @Operation(summary = "Get reading list", description = "Get reading list of news by user, admin or moderator", tags = {"user-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class))))})
    @GetMapping(value = "/reading/")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getReadingList() {
        logger.debug("REST request to get reading list");
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final Set<News> readingList = new HashSet<>(userDetails.getMarkedNews());
        final ArrayList<NewsDTO> newsDTOs = new ArrayList<>();
        NewsDTO newsDTO;
        for (News news : readingList) {
            newsDTO = modelMapper.map(news, NewsDTO.class);
            newsDTO.setSelfLiked(likeService.checkIfLikeExists(PostType.NEWS, news.getNewsId(), userDetails.getId()));
            newsDTO.setLikesCount(likeService.getNumberOfLikesByPostTypeAndPostId(PostType.NEWS, news.getNewsId()));
            newsDTOs.add(newsDTO);
        }
        return new ResponseEntity<>(utilService.getResponseBody(newsDTOs), HttpStatus.OK);
    }
}
