package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsImpl;
import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.NewsDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.exception.NoPermissionException;
import com.zipline.model.News;
import com.zipline.page.NewsPage;
import com.zipline.criteria.NewsSearchCriteria;
import com.zipline.model.PostType;
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

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type News feed controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/news")
@Tag(name = "news-feed-controller", description = "News feed API")
public class NewsFeedController {
    private static final Logger logger = LoggerFactory.getLogger(NewsFeedController.class);
    private final NewsFeedService newsFeedService;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final UtilService utilService;
    private final CryptopanicService cryptopanicService;
    private final LikeService likeService;
    private final CommentService commentService;

    /**
     * Instantiates a new News feed controller.
     *
     * @param newsFeedService    the news feed service
     * @param modelMapper        the model mapper
     * @param userDetailsService the user details service
     * @param utilService        the util service
     * @param cryptopanicService the cryptopanic service
     * @param likeService        the like service
     * @param commentService     the comment service
     */
    @Autowired
    public NewsFeedController(final NewsFeedService newsFeedService,
                              final ModelMapper modelMapper,
                              final UserDetailsServiceImpl userDetailsService,
                              final UtilService utilService,
                              final CryptopanicService cryptopanicService,
                              final LikeService likeService,
                              final CommentService commentService) {
        this.newsFeedService = newsFeedService;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.utilService = utilService;
        this.cryptopanicService = cryptopanicService;
        this.likeService = likeService;
        this.commentService = commentService;
    }

    /**
     * Gets all news.
     *
     * @param newsPage           the news page
     * @param newsSearchCriteria the news search criteria
     * @return the all news
     */
    @Operation(summary = "Find all news", description = "Get all news for user, moderator or admin", tags = {"news-feed-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class))))})
    @GetMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllNews(
            final @ApiParam(value = "News page parameters") NewsPage newsPage,
            final @ApiParam(value = "News search criteria parameters") NewsSearchCriteria newsSearchCriteria) {
        logger.debug("REST request to get all news");
        final List<NewsDTO> newsDTOs = new ArrayList<>();
        final Page<News> pageNews = newsFeedService.getNews(newsPage, newsSearchCriteria);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final List<News> newsList = pageNews.getContent();
        NewsDTO newsDTO;
        for (News news : newsList) {
            newsDTO = modelMapper.map(news, NewsDTO.class);
            newsDTO.setSelfLiked(likeService.checkIfLikeExists(PostType.NEWS, news.getNewsId(), userDetails.getId()));
            newsDTO.setLikesCount(likeService.getNumberOfLikesByPostTypeAndPostId(PostType.NEWS, news.getNewsId()));
            newsDTO.setNumberOfComments(commentService.getNumberOfCommentsForPost(PostType.NEWS, news.getNewsId()));
            newsDTOs.add(newsDTO);
        }
        return new ResponseEntity<>(utilService.getResponseBody(newsDTOs), HttpStatus.OK);
    }

    /**
     * Gets news by id.
     *
     * @param newsId the news id
     * @return the news
     */
    @Operation(summary = "Get news by id", description = "Get news by news id for user, moderator or admin", tags = {"news-feed-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class)))),
            @ApiResponse(responseCode = "404", description = "No such news",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping(value = "/{newsId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getNews(
            final @ApiParam(value = "News id to be loaded") @PathVariable("newsId") Long newsId) {
        logger.debug("REST request to news by id {}", newsId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        NewsDTO newsDTO = modelMapper.map(newsFeedService.getNewsById(newsId), NewsDTO.class);
        newsDTO.setSelfLiked(likeService.checkIfLikeExists(PostType.NEWS, newsId, userDetails.getId()));
        newsDTO.setLikesCount(likeService.getNumberOfLikesByPostTypeAndPostId(PostType.NEWS, newsId));
        newsDTO.setNumberOfComments(commentService.getNumberOfCommentsForPost(PostType.NEWS, newsId));
        return new ResponseEntity<>(utilService.getResponseBody(newsDTO), HttpStatus.OK);
    }

    /**
     * Create news response entity.
     *
     * @param newsDTO the news dto
     * @return the response entity
     */
    @Operation(summary = "Create news", description = "Create news by moderator or admin", tags = {"news-feed-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "News has been created",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createNews(
            final @ApiParam(value = "News entity to create") @Valid @RequestBody NewsDTO newsDTO) {
        logger.debug("REST request to create news");
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        newsDTO.setCreatedBy(userDetails.getId());
        newsDTO.setCreated(LocalDateTime.now());
        final News news = newsFeedService.save(modelMapper.map(newsDTO, News.class));
        return new ResponseEntity<>(utilService.getResponseBody(modelMapper.map(news, NewsDTO.class)), HttpStatus.CREATED);
    }

    /**
     * Update news response entity.
     *
     * @param newsDTO the news dto
     * @param newsId  the news id
     * @return the response entity
     */
    @Operation(summary = "Update news", description = "Update news by id for moderator or admin", tags = {"news-feed-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successful update",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class)))),
            @ApiResponse(responseCode = "403", description = "No permission to update the news",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No such news",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PutMapping(value = "/{newsId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateNews(
            final @ApiParam(value = "News entity to be updated") @Valid @RequestBody NewsDTO newsDTO,
            final @ApiParam(value = "News id to be updated") @PathVariable(value = "newsId") Long newsId) {
        logger.debug("REST request to update news by id {}", newsId);
        final News loadedNews = newsFeedService.getNewsById(newsId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        if (!loadedNews.getCreatedBy().equals(userDetails.getId())) {
            throw new NoPermissionException(userDetails.getUsername(), userDetailsService.loadUserByUserId(loadedNews.getCreatedBy()).getUsername(), "update the news");
        }
        loadedNews.setTitle(newsDTO.getTitle());
        loadedNews.setContent(newsDTO.getContent());
        loadedNews.setDescription(newsDTO.getDescription());
        loadedNews.setSource(newsDTO.getSource());
        loadedNews.setUpdated(LocalDateTime.now());
        final News news = newsFeedService.save(modelMapper.map(loadedNews, News.class));
        return new ResponseEntity<>(utilService.getResponseBody(modelMapper.map(news, NewsDTO.class)), HttpStatus.ACCEPTED);
    }

    /**
     * Delete news by id response entity.
     *
     * @param newsId the news id
     * @return the response entity
     */
    @Operation(summary = "Delete news", description = "Delete news by id by moderator or admin", tags = {"news-feed-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful delete",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class)))),
            @ApiResponse(responseCode = "403", description = "No permission to delete the news",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "No such news",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @DeleteMapping(value = "/delete/{newsId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteNewsById(
            final @ApiParam(value = "News id to be deleted") @PathVariable(value = "newsId") Long newsId) {
        logger.debug("REST request to delete news by id {}", newsId);
        final News loadedNews = newsFeedService.getNewsById(newsId);
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        if (!loadedNews.getCreatedBy().equals(userDetails.getId())) {
            throw new NoPermissionException(userDetails.getUsername(), userDetailsService.loadUserByUserId(loadedNews.getCreatedBy()).getUsername(), "delete the news");
        }
        newsFeedService.deleteByNewsId(newsId);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }

    /**
     * Gets news by tag.
     *
     * @param tag the tag
     * @return the news by tag
     */
    @ApiIgnore
    @Operation(summary = "Find news by tag", description = "Find news with the tag by moderator, user or admin", tags = {"news-feed-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful delete",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NewsDTO.class))))})
    @GetMapping(value = "/tag/{tag}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getNewsByTag(
            final @ApiParam(value = "Tag to find corresponding news") @PathVariable(value = "tag") String tag) {
        logger.debug("REST request to get news by tag {}", tag);
        final List<NewsDTO> newsDTOs = new ArrayList<>();
        for (News news : newsFeedService.findAllByTag(tag))
            newsDTOs.add(modelMapper.map(news, NewsDTO.class));
        return new ResponseEntity<>(utilService.getResponseBody(newsDTOs), HttpStatus.OK);
    }
}
