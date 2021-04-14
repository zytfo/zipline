package com.zipline.repository;

import com.zipline.model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The type File db repository.
 */
@Repository
public interface FileDBRepository extends JpaRepository<FileDB, UUID> {
}
