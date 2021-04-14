package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.ResponseFileDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.model.FileDB;
import com.zipline.service.FileStorageService;
import com.zipline.service.UtilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type File controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/files")
@Tag(name = "files-controller", description = "Files API")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final FileStorageService fileStorageService;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final UtilService utilService;

    /**
     * Instantiates a new File controller.
     *
     * @param fileStorageService the file storage service
     * @param modelMapper        the model mapper
     * @param userDetailsService the user details service
     * @param utilService        the util service
     */
    public FileController(final FileStorageService fileStorageService,
                          final ModelMapper modelMapper,
                          final UserDetailsServiceImpl userDetailsService,
                          final UtilService utilService) {
        this.fileStorageService = fileStorageService;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.utilService = utilService;
    }

    /**
     * Upload file response entity.
     *
     * @param file the file
     * @return the response entity
     */
    @Operation(summary = "Upload a new file", description = "Upload a new file by user, moderator or admin", tags = {"files-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "A file has been uploaded",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseFileDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Something went wrong",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> uploadFile(final @RequestPart("file") MultipartFile file) {
        String message;
        logger.debug("REST request to upload a new file");
        try {
            final FileDB fileDB = fileStorageService.store(file);
            message = "Image id: " + fileDB.getFileId();
            return new ResponseEntity<>(utilService.getResponseBody(message), HttpStatus.CREATED);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return new ResponseEntity<>(utilService.getResponseBody(message), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets list files.
     *
     * @return the list files
     */
    @Operation(summary = "Get files", description = "Get files by user, moderator or admin", tags = {"files-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseFileDTO.class))))})
    @GetMapping("/")
    //TODO: think about permissions
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getListFiles() {
        final List<ResponseFileDTO> files = fileStorageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/files/")
                    .path(dbFile.getFileId().toString())
                    .toUriString();
            return new ResponseFileDTO(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(utilService.getResponseBody(files), HttpStatus.OK);
    }

    /**
     * Gets file.
     *
     * @param id the id
     * @return the file
     */
    @Operation(summary = "Get file by id", description = "Get file by id by user, moderator or admin", tags = {"files-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseFileDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(final @PathVariable UUID id) {
        logger.debug("REST request to get a file");
        Optional<FileDB> fileDB = fileStorageService.getFile(id);
        if (!fileDB.isPresent())
            return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.NOT_FOUND);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.get().getName() + "\"")
                .body(fileDB.get().getData());
    }
}
