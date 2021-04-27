package com.zipline.repository;

import com.zipline.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The news repository contains methods for news entities.
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    /**
     * Find all news created by a certain user.
     *
     * @param userId the user id
     * @return the list of news entities
     */
    List<News> findAllByCreatedBy(@Param("userId") Long userId);

    /**
     * Delete all news created by a certain user.
     *
     * @param userId the user id
     */
    void deleteNewsByCreatedBy(@Param("userId") Long userId);

    /**
     * Delete news by its id.
     *
     * @param newsId the news id
     */
    void deleteByNewsId(@Param("newsId") Long newsId);

    /**
     * Find all news in a certain period.
     *
     * @param start the start of the period
     * @param end   the end of the period
     * @return the list of news in a certain period
     */
    @Query(value = "SELECT * FROM news WHERE created >= ?1 AND created <= ?2", nativeQuery = true)
    List<News> findNewsByDatetimePeriod(LocalDateTime start, LocalDateTime end);

    /**
     * Find all by tag list.
     *
     * @param tag the tag
     * @return the list
     */
    @Query(value = "SELECT * FROM news WHERE tags ILIKE ALL(CONCAT('%', :tag, '%'))", nativeQuery = true)
    List<News> findAllByTag(@Param("tag") String tag);
}
