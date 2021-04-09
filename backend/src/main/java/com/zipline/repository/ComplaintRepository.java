package com.zipline.repository;

import com.zipline.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Complaint repository.
 */
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    /**
     * Find all by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<Complaint> findAllByUserId(@Param("userId") Long userId);

    /**
     * Find all by post author id list.
     *
     * @param postAuthorId the post author id
     * @return the list
     */
    List<Complaint> findAllByPostAuthorId(@Param("postAuthorId") Long postAuthorId);
}
