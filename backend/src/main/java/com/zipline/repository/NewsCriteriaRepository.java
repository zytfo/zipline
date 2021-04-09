package com.zipline.repository;

import com.zipline.model.News;
import com.zipline.page.NewsPage;
import com.zipline.criteria.NewsSearchCriteria;
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
 * The type News criteria repository.
 */
@Repository
public class NewsCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    /**
     * Instantiates a new News criteria repository.
     *
     * @param entityManager the entity manager
     */
    public NewsCriteriaRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    /**
     * Find a ll with filters page.
     *
     * @param newsPage           the news page
     * @param newsSearchCriteria the news search criteria
     * @return the page
     */
    public Page<News> findALlWithFilters(final NewsPage newsPage, final NewsSearchCriteria newsSearchCriteria) {
        final CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        final Root<News> newsRoot = criteriaQuery.from(News.class);
        final Predicate predicate = getPredicate(newsSearchCriteria, newsRoot);
        criteriaQuery.where(predicate);
        setOrder(newsPage, criteriaQuery, newsRoot);

        final TypedQuery<News> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(newsPage.getPageNumber() * newsPage.getPageSize());
        typedQuery.setMaxResults(newsPage.getPageSize());

        final Pageable pageable = getPageable(newsPage);
        final long newsCount = getNewsCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, newsCount);
    }

    private Predicate getPredicate(final NewsSearchCriteria newsSearchCriteria,
                                   final Root<News> newsRoot) {
        final List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(newsSearchCriteria.getTitle())) {
            predicates.add(
                    criteriaBuilder.like(newsRoot.get("title"),
                            "%" + newsSearchCriteria.getTitle() + "%")
            );
        }
        if (Objects.nonNull(newsSearchCriteria.getDescription())) {
            predicates.add(
                    criteriaBuilder.like(newsRoot.get("description"),
                            "%" + newsSearchCriteria.getDescription() + "%")
            );
        }
        if (Objects.nonNull(newsSearchCriteria.getContent())) {
            predicates.add(
                    criteriaBuilder.like(newsRoot.get("content"),
                            "%" + newsSearchCriteria.getContent() + "%")
            );
        }
        if (Objects.nonNull(newsSearchCriteria.getSource())) {
            predicates.add(
                    criteriaBuilder.like(newsRoot.get("source"),
                            "%" + newsSearchCriteria.getSource() + "%")
            );
        }
        if (Objects.nonNull(newsSearchCriteria.getCreated())) {
            final LocalDate localDate = newsSearchCriteria.getCreated().toLocalDate();
            final LocalDateTime startDay = localDate.atStartOfDay();
            final LocalDateTime endDay = localDate.atTime(LocalTime.MAX);
            predicates.add(
                    criteriaBuilder.between(newsRoot.get("created"), startDay, endDay)
            );
        }
        if (Objects.nonNull(newsSearchCriteria.getTags())) {
            for (String tag : newsSearchCriteria.getTags()) {
                Expression<String> delimiter = criteriaBuilder.literal(",");
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.function(
                                "array_to_string", String.class, newsRoot.get("tags"), delimiter),
                        "%" + tag.toLowerCase() + "%"));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(final NewsPage newsPage,
                          final CriteriaQuery<News> criteriaQuery,
                          final Root<News> newsRoot) {
        if (newsPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(newsRoot.get(newsPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(newsRoot.get(newsPage.getSortBy())));
        }
    }

    private Pageable getPageable(final NewsPage newsPage) {
        final Sort sort = Sort.by(newsPage.getSortDirection(), newsPage.getSortBy());
        return PageRequest.of(newsPage.getPageNumber(), newsPage.getPageSize(), sort);
    }

    private long getNewsCount(Predicate predicate) {
        final CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        final Root<News> countRoot = countQuery.from(News.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
