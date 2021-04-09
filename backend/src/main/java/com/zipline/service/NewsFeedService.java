package com.zipline.service;

import com.zipline.exception.NoSuchNewsException;
import com.zipline.model.News;
import com.zipline.page.NewsPage;
import com.zipline.criteria.NewsSearchCriteria;
import com.zipline.repository.NewsCriteriaRepository;
import com.zipline.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * News feed service.
 */
@Service
@Transactional
public class NewsFeedService {
    private final NewsRepository newsRepository;
    private final NewsCriteriaRepository newsCriteriaRepository;

    /**
     * Instantiates a new News feed service.
     *
     * @param newsRepository         the news repository
     * @param newsCriteriaRepository the news criteria repository
     */
    @Autowired
    public NewsFeedService(final NewsRepository newsRepository,
                           final NewsCriteriaRepository newsCriteriaRepository) {
        this.newsRepository = newsRepository;
        this.newsCriteriaRepository = newsCriteriaRepository;
    }

    /**
     * Gets news by id.
     *
     * @param newsId the news id
     * @return the news by id
     */
    public News getNewsById(final Long newsId) {
        Optional<News> news = newsRepository.findById(newsId);
        return news.orElseThrow(
                () -> new NoSuchNewsException(newsId)
        );
    }

    /**
     * Save news.
     *
     * @param news the news
     * @return the news
     */
    public News save(final News news) {
        return newsRepository.save(news);
    }

    /**
     * Gets news by user id.
     *
     * @param userId the user id
     * @return the news by user id
     */
    public List<News> getNewsByUserId(final Long userId) {
        return new ArrayList<>(newsRepository.findAllByCreatedBy(userId));
    }

    /**
     * Find all news.
     *
     * @return the list of all news
     */
    public List<News> findAll() {
        return new ArrayList<>(newsRepository.findAll());
    }

    /**
     * Delete news by user id.
     *
     * @param userId the user id
     */
    public void deleteByUserId(final Long userId) {
        newsRepository.deleteNewsByCreatedBy(userId);
    }

    /**
     * Delete by news id.
     *
     * @param newsId the news id
     */
    public void deleteByNewsId(final Long newsId) {
        newsRepository.deleteByNewsId(newsId);
    }

    /**
     * Gets news in a certain period of time.
     *
     * @param start the start
     * @param end   the end
     * @return the news in a certain period of time
     */
    public List<News> getNewsByCreated(final LocalDateTime start, final LocalDateTime end) {
        return new ArrayList<>(newsRepository.findNewsByDatetimePeriod(start, end));
    }

    /**
     * Find all by tag list.
     *
     * @param tag the tag
     * @return the list
     */
    public List<News> findAllByTag(final String tag) {
        return new ArrayList<>(newsRepository.findAllByTag(tag));
    }

    /**
     * Gets news.
     *
     * @param newsPage           the news page
     * @param newsSearchCriteria the news search criteria
     * @return the news
     */
    public Page<News> getNews(final NewsPage newsPage, final NewsSearchCriteria newsSearchCriteria) {
        return newsCriteriaRepository.findALlWithFilters(newsPage, newsSearchCriteria);
    }
}
