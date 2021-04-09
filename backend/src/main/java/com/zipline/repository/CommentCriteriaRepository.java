package com.zipline.repository;

import com.zipline.auth.security.services.UserDetailsImpl;
import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.model.Comment;
import com.zipline.page.CommentPage;
import com.zipline.criteria.CommentSearchCriteria;
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
 * The type Comment criteria repository.
 */
@Repository
public class CommentCriteriaRepository {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Instantiates a new Comment criteria repository.
     *
     * @param entityManager      the entity manager
     * @param userDetailsService the user details service
     */
    public CommentCriteriaRepository(final EntityManager entityManager,
                                     final UserDetailsServiceImpl userDetailsService) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.userDetailsService = userDetailsService;
    }

    /**
     * Find a ll with filters page.
     *
     * @param commentPage           the comment page
     * @param commentSearchCriteria the comment search criteria
     * @return the page
     */
    public Page<Comment> findALlWithFilters(final CommentPage commentPage, final CommentSearchCriteria commentSearchCriteria) {
        final CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        final Root<Comment> commentRoot = criteriaQuery.from(Comment.class);
        final Predicate predicate = getPredicate(commentSearchCriteria, commentRoot);
        criteriaQuery.where(predicate);
        setOrder(commentPage, criteriaQuery, commentRoot);

        final TypedQuery<Comment> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(commentPage.getPageNumber() * commentPage.getPageSize());
        typedQuery.setMaxResults(commentPage.getPageSize());

        final Pageable pageable = getPageable(commentPage);
        final long commentCount = getCommentCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, commentCount);
    }

    private Predicate getPredicate(final CommentSearchCriteria commentSearchCriteria,
                                   final Root<Comment> commentRoot) {
        final List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(commentSearchCriteria.getPostType())) {
            predicates.add(
                    criteriaBuilder.equal(commentRoot.get("postType"), commentSearchCriteria.getPostType())
            );
        }
        if (Objects.nonNull(commentSearchCriteria.getCreated())) {
            final LocalDate localDate = commentSearchCriteria.getCreated().toLocalDate();
            final LocalDateTime startDay = localDate.atStartOfDay();
            final LocalDateTime endDay = localDate.atTime(LocalTime.MAX);
            predicates.add(
                    criteriaBuilder.between(commentRoot.get("created"), startDay, endDay)
            );
        }
        //TODO Something wrong here
        if (Objects.nonNull(commentSearchCriteria.getAuthor())) {
            final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(commentSearchCriteria.getAuthor());
            predicates.add(
                    criteriaBuilder.equal(commentRoot.get("userId"), userDetails.getId())
            );
        }
        if (Objects.nonNull(commentSearchCriteria.getPostId())) {
            predicates.add(
                    criteriaBuilder.equal(commentRoot.get("postId"), commentSearchCriteria.getPostId())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(final CommentPage commentPage,
                          final CriteriaQuery<Comment> criteriaQuery,
                          final Root<Comment> commentRoot) {
        if (commentPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(commentRoot.get(commentPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(commentRoot.get(commentPage.getSortBy())));
        }
    }

    private Pageable getPageable(final CommentPage commentPage) {
        final Sort sort = Sort.by(commentPage.getSortDirection(), commentPage.getSortBy());
        return PageRequest.of(commentPage.getPageNumber(), commentPage.getPageSize(), sort);
    }

    private long getCommentCount(Predicate predicate) {
        final CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        final Root<Comment> countRoot = countQuery.from(Comment.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
