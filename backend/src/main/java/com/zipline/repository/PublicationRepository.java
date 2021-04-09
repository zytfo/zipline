package com.zipline.repository;

import com.zipline.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The publication repository contains methods for publication entities.
 */
@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    /**
     * Find all publications created by a certain user.
     *
     * @param userId the user id
     * @return the list of all publications created by a certain user
     */
    List<Publication> findAllByCreatedBy(@Param("userId") Long userId);

    /**
     * Delete publication created by a certain user.
     *
     * @param userId the user id
     */
    void deletePublicationByCreatedBy(@Param("userId") Long userId);

    /**
     * Delete by publication id.
     *
     * @param publicationId the publication id
     */
    void deleteByPublicationId(@Param("publicationId") Long publicationId);

    /**
     * Find all publications in a certain period.
     *
     * @param start the start of the period
     * @param end   the end of the period
     * @return the list of publications in a certain period
     */
    @Query(value = "SELECT * FROM publications WHERE created >= :start AND created <= :end", nativeQuery = true)
    List<Publication> findPublicationByDatetimePeriod(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    /**
     * Find all publications by keyword.
     *
     * @param ticker the ticker
     * @return the list
     */
    @Query(value = "SELECT * FROM publications WHERE content ILIKE CONCAT('%$', :ticker, '%')", nativeQuery = true)
    List<Publication> findAllByTicker(@Param("ticker") String ticker);

    /**
     * Find all by tag list.
     *
     * @param tag the tag
     * @return the list
     */
    @Query(value = "SELECT * FROM publications WHERE content ILIKE CONCAT('%#', :tag, '%')", nativeQuery = true)
    List<Publication> findAllByTag(@Param("tag") String tag);
}
