package com.zipline.service;

import com.zipline.exception.NoSuchPublicationException;
import com.zipline.model.Publication;
import com.zipline.page.PublicationPage;
import com.zipline.criteria.PublicationSearchCriteria;
import com.zipline.repository.PublicationCriteriaRepository;
import com.zipline.repository.PublicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Publication service.
 */
@Service
@Transactional
public class PublicationService {
    private static final Logger logger = LoggerFactory.getLogger(PublicationService.class);
    private final PublicationRepository publicationRepository;
    private final PublicationCriteriaRepository publicationCriteriaRepository;

    /**
     * Instantiates a new Publication service.
     *
     * @param publicationRepository         the publication repository
     * @param publicationCriteriaRepository the publication criteria repository
     */
    @Autowired
    public PublicationService(final PublicationRepository publicationRepository,
                              final PublicationCriteriaRepository publicationCriteriaRepository) {
        this.publicationRepository = publicationRepository;
        this.publicationCriteriaRepository = publicationCriteriaRepository;
    }

    /**
     * Gets publication by id.
     *
     * @param publicationId the publication id
     * @return the publication by id
     */
    public Publication getPublicationById(final Long publicationId) {
        Optional<Publication> publication = publicationRepository.findById(publicationId);
        return publication.orElseThrow(
                () -> new NoSuchPublicationException(publicationId)
        );
    }

    /**
     * Save publication.
     *
     * @param publication the publication
     * @return the publication
     */
    public Publication save(final Publication publication) {
        return publicationRepository.save(publication);
    }

    /**
     * Gets publication by user id.
     *
     * @param userId the user id
     * @return the publication by user id
     */
    public List<Publication> getPublicationByUserId(final Long userId) {
        return new ArrayList<>(publicationRepository.findAllByCreatedBy(userId));
    }

    /**
     * Find all publications.
     *
     * @return the list
     */
    public List<Publication> findAll() {
        return new ArrayList<>(publicationRepository.findAll());
    }

    /**
     * Delete publication by user id.
     *
     * @param userId the user id
     */
    public void deleteByUserId(final Long userId) {
        publicationRepository.deletePublicationByCreatedBy(userId);
    }

    /**
     * Delete by publication id.
     *
     * @param publicationId the publication id
     */
    public void deleteByPublicationId(final Long publicationId) {
        publicationRepository.deleteByPublicationId(publicationId);
    }

    /**
     * Gets publication in a certain period of time.
     *
     * @param start the start
     * @param end   the end
     * @return the publication a certain period of time
     */
    public List<Publication> getPublicationByCreated(final LocalDateTime start, final LocalDateTime end) {
        return new ArrayList<>(publicationRepository.findPublicationByDatetimePeriod(start, end));
    }

    /**
     * Gets publications by ticker.
     *
     * @param ticker the ticker
     * @return list of publications by keyword
     */
    public List<Publication> findAllByTicker(final String ticker) {
        return new ArrayList<>(publicationRepository.findAllByTicker(ticker));
    }

    /**
     * Gets publications by tag.
     *
     * @param tag the tag
     * @return list of publications by keyword
     */
    public List<Publication> findAllByTag(final String tag) {
        return new ArrayList<>(publicationRepository.findAllByTag(tag));
    }

    /**
     * Gets publications.
     *
     * @param publicationPage           the publication page
     * @param publicationSearchCriteria the publication search criteria
     * @return the publications
     */
    public Page<Publication> getPublications(final PublicationPage publicationPage,
                                             final PublicationSearchCriteria publicationSearchCriteria) {
        return publicationCriteriaRepository.findALlWithFilters(publicationPage, publicationSearchCriteria);
    }
}
