package com.zipline.repository;

import com.zipline.auth.security.services.UserDetailsImpl;
import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.model.Publication;
import com.zipline.page.PublicationPage;
import com.zipline.criteria.PublicationSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Publication criteria repository.
 */
@Repository
public class PublicationCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Instantiates a new Publication criteria repository.
     *
     * @param entityManager      the entity manager
     * @param userDetailsService the user details service
     */
    public PublicationCriteriaRepository(final EntityManager entityManager,
                                         final UserDetailsServiceImpl userDetailsService) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.userDetailsService = userDetailsService;
    }

    /**
     * Find a ll with filters page.
     *
     * @param publicationPage           the publication page
     * @param publicationSearchCriteria the publication search criteria
     * @return the page
     */
    public Page<Publication> findALlWithFilters(final PublicationPage publicationPage, final PublicationSearchCriteria publicationSearchCriteria) {
        final CriteriaQuery<Publication> criteriaQuery = criteriaBuilder.createQuery(Publication.class);
        final Root<Publication> publicationRoot = criteriaQuery.from(Publication.class);
        final Predicate predicate = getPredicate(publicationSearchCriteria, publicationRoot);
        criteriaQuery.where(predicate);
        setOrder(publicationPage, criteriaQuery, publicationRoot);

        final TypedQuery<Publication> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(publicationPage.getPageNumber() * publicationPage.getPageSize());
        typedQuery.setMaxResults(publicationPage.getPageSize());

        final Pageable pageable = getPageable(publicationPage);
        final long publicationCount = getPublicationCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, publicationCount);
    }

    private Predicate getPredicate(final PublicationSearchCriteria publicationSearchCriteria,
                                   final Root<Publication> publicationRoot) {
        final List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(publicationSearchCriteria.getContent())) {
            predicates.add(
                    criteriaBuilder.like(publicationRoot.get("content"),
                            "%" + publicationSearchCriteria.getContent() + "%")
            );
        }
        if (Objects.nonNull(publicationSearchCriteria.getCreatedBy())) {
            final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(publicationSearchCriteria.getCreatedBy());
            predicates.add(
                    criteriaBuilder.equal(publicationRoot.get("createdBy"), userDetails.getId())
            );
        }
        if (Objects.nonNull(publicationSearchCriteria.getCreated())) {
            final LocalDate localDate = publicationSearchCriteria.getCreated().toLocalDate();
            final LocalDateTime startDay = localDate.atStartOfDay();
            final LocalDateTime endDay = localDate.atTime(LocalTime.MAX);
            predicates.add(
                    criteriaBuilder.between(publicationRoot.get("created"), startDay, endDay)
            );
        }
        if (Objects.nonNull(publicationSearchCriteria.getTickers())) {
            for (String tag : publicationSearchCriteria.getTickers()) {
                Expression<String> delimiter = criteriaBuilder.literal(",");
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.function(
                                "array_to_string", String.class, publicationRoot.get("tickers"), delimiter),
                        "%" + tag.toLowerCase() + "%"));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(final PublicationPage publicationPage,
                          final CriteriaQuery<Publication> criteriaQuery,
                          final Root<Publication> publicationRoot) {
        if (publicationPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(publicationRoot.get(publicationPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(publicationRoot.get(publicationPage.getSortBy())));
        }
    }

    private Pageable getPageable(final PublicationPage publicationPage) {
        final Sort sort = Sort.by(publicationPage.getSortDirection(), publicationPage.getSortBy());
        return PageRequest.of(publicationPage.getPageNumber(), publicationPage.getPageSize(), sort);
    }

    private long getPublicationCount(Predicate predicate) {
        final CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        final Root<Publication> countRoot = countQuery.from(Publication.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
