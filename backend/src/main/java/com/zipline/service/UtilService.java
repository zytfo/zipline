package com.zipline.service;

import com.zipline.exception.NoSuchPostForPostTypeException;
import com.zipline.exception.NoSuchPostTypeException;
import com.zipline.exception.NoSuchTickerException;
import com.zipline.model.PostType;
import com.zipline.model.Status;
import com.zipline.repository.CommentRepository;
import com.zipline.repository.CurrencyRepository;
import com.zipline.repository.NewsRepository;
import com.zipline.repository.PublicationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * The type Util service.
 */
@Service
@Transactional
public class UtilService {
    private final NewsRepository newsRepository;
    private final PublicationRepository publicationRepository;
    private final CurrencyRepository currencyRepository;
    private final CommentRepository commentRepository;

    /**
     * Instantiates a new Util service.
     *
     * @param newsRepository        the news repository
     * @param publicationRepository the publication repository
     * @param currencyRepository    the currency repository
     * @param commentRepository     the comment repository
     */
    public UtilService(final NewsRepository newsRepository,
                       final PublicationRepository publicationRepository,
                       final CurrencyRepository currencyRepository,
                       final CommentRepository commentRepository) {
        this.newsRepository = newsRepository;
        this.publicationRepository = publicationRepository;
        this.currencyRepository = currencyRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * Gets post type by string.
     *
     * @param postType the post type
     * @return the post type by string
     */
    public PostType getPostTypeByString(final String postType) {
        final String type = postType.toUpperCase();
        if (Stream.of(PostType.values()).anyMatch(v -> v.name().equals(type))) {
            return PostType.valueOf(type);
        } else {
            throw new NoSuchPostTypeException(postType);
        }
    }

    /**
     * Check if there is a such post of a certain type and a certain id.
     *
     * @param postType the post type
     * @param postId   the post id
     */
    public void checkPostTypeAndPostId(final PostType postType, final Long postId) {
        switch (postType) {
            case NEWS:
                if (newsRepository.existsById(postId)) {
                    return;
                } else {
                    throw new NoSuchPostForPostTypeException(postType, postId);
                }
            case PUBLICATION:
                if (publicationRepository.existsById(postId)) {
                    return;
                } else {
                    throw new NoSuchPostForPostTypeException(postType, postId);
                }
            case COMMENT:
                if (commentRepository.existsById(postId)) {
                    return;
                } else {
                    throw new NoSuchPostForPostTypeException(postType, postId);
                }
            default:
                throw new NoSuchPostForPostTypeException(postType, postId);
        }
    }

    /**
     * Ticker does exist boolean.
     *
     * @param ticker the ticker
     * @return the boolean
     */
    public void tickerDoesExist(final String ticker) {
        if (!currencyRepository.existsByTicker(ticker.toUpperCase())) {
            throw new NoSuchTickerException(ticker.toUpperCase());
        }
    }

    /**
     * Gets response body.
     *
     * @param data the data
     * @return the response body
     */
    public Map<String, Object> getResponseBody(final Object data) {
        final HashMap<String, Object> body = new HashMap<>();
        body.put("data", data);
        body.put("status", Status.getSuccessStatus());
        return body;
    }

    /**
     * Gets empty response body.
     *
     * @return the empty response body
     */
    public Map<String, Object> getEmptyResponseBody() {
        final HashMap<String, Object> body = new HashMap<>();
        body.put("status", Status.getSuccessStatus());
        return body;
    }

    /**
     * Gets too many requests response.
     *
     * @param timeToRefill the time to refill
     * @return the too many requests response
     */
    public Map<String, Object> getTooManyRequestsResponse(final String timeToRefill) {
        final HashMap<String, Object> body = new HashMap<>();
        body.put("X-Rate-Limit-Retry-After-Milliseconds", timeToRefill);
        body.put("status", Status.getTooManyRequestsStatus());
        return body;
    }

    /**
     * Extract tickers list.
     *
     * @param content the content
     * @return the list
     */
    public List<String> extractTickers(final String content) {
        final List<String> tickers = new ArrayList<>();
        final Matcher matcher = Pattern.compile("\\$(\\w+)").matcher(content);
        while (matcher.find()) {
            tickers.add(matcher.group(1).toLowerCase());
        }
        return tickers;
    }
}
