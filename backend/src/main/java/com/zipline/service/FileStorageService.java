package com.zipline.service;

import com.zipline.model.FileDB;
import com.zipline.repository.FileDBRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * The type File storage service.
 */
@Service
@Transactional
public class FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private final FileDBRepository fileDBRepository;

    /**
     * Instantiates a new File storage service.
     *
     * @param fileDBRepository the file db repository
     */
    public FileStorageService(final FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }

    /**
     * Store file db.
     *
     * @param file the file
     * @return the file db
     * @throws IOException the io exception
     */
    public FileDB store(final MultipartFile file) throws IOException {
        final String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        final FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        return fileDBRepository.save(fileDB);
    }

    /**
     * Gets file.
     *
     * @param id the id
     * @return the file
     */
    public Optional<FileDB> getFile(final UUID id) {
        return fileDBRepository.findById(id);
    }

    /**
     * Delete file.
     *
     * @param id the id
     */
    public void deleteFile(final UUID id) {
        fileDBRepository.deleteById(id);
    }

    /**
     * Gets all files.
     *
     * @return the all files
     */
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
