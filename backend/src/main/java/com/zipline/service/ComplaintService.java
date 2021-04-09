package com.zipline.service;

import com.zipline.exception.NoSuchComplaintException;
import com.zipline.model.Complaint;
import com.zipline.repository.ComplaintRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Complaint service.
 */
@Service
@Transactional
public class ComplaintService {
    private static final Logger logger = LoggerFactory.getLogger(ComplaintService.class);
    private final ComplaintRepository complaintRepository;

    /**
     * Instantiates a new Complaint service.
     *
     * @param complaintRepository the complaint repository
     */
    @Autowired
    public ComplaintService(final ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    /**
     * Save complaint.
     *
     * @param complaint the complaint
     * @return the complaint
     */
    public Complaint save(final Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    /**
     * Delete by complaint id.
     *
     * @param complaintId the complaint id
     */
    public void deleteByComplaintId(final Long complaintId) {
        complaintRepository.deleteById(complaintId);
    }

    /**
     * Gets complaint by id.
     *
     * @param complaintId the complaint id
     * @return the complaint by id
     */
    public Complaint getComplaintById(final Long complaintId) {
        Optional<Complaint> complaint = complaintRepository.findById(complaintId);
        return complaint.orElseThrow(
                () -> new NoSuchComplaintException(complaintId)
        );
    }

    /**
     * Find by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    public List<Complaint> findByUserId(final Long userId) {
        return new ArrayList<>(complaintRepository.findAllByUserId(userId));
    }

    /**
     * Find by post author id list.
     *
     * @param postAuthorId the post author id
     * @return the list
     */
    public List<Complaint> findByPostAuthorId(final Long postAuthorId) {
        return new ArrayList<>(complaintRepository.findAllByPostAuthorId(postAuthorId));
    }

}
