package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.NFTDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.model.NFT;
import com.zipline.service.NFTService;
import com.zipline.service.UtilService;
import io.swagger.annotations.ApiParam;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigInteger;

/**
 * The type NFT controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/nfts")
@Tag(name = "nft-controller", description = "NFT API")
public class NFTController {
    private static final Logger logger = LoggerFactory.getLogger(NFTController.class);
    private final NFTService nftService;
    private final UtilService utilService;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Instantiates a new NFT controller.
     *
     * @param nftService         the NFT service
     * @param utilService        the util service
     * @param modelMapper        the model mapper
     * @param userDetailsService the user details service
     */
    public NFTController(final NFTService nftService, UtilService utilService, ModelMapper modelMapper, UserDetailsServiceImpl userDetailsService) {
        this.nftService = nftService;
        this.utilService = utilService;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Gets NFT by user.
     *
     * @return the NFT
     */
    @Operation(summary = "Get NFT", description = "Get NFTs of the user", tags = {"nft-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NFT.class))))})
    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getNFTs() {
        logger.debug("REST request to get all NFTs of the user");
        return new ResponseEntity<>(utilService.getResponseBody(
                nftService.getNFTsOfUser(userDetailsService.getUser().getId())), HttpStatus.OK);
    }

    /**
     * Gets NFT by ID.
     *
     * @param nftId the nft id
     * @return the NFT
     */
    @Operation(summary = "Get NFT by ID", description = "Get NFT by ID", tags = {"nft-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NFT.class))))})
    @GetMapping("/{nftId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getNFTById(final @ApiParam(value = "Id of the NFT to retrieve") @PathVariable(value = "nftId") BigInteger nftId) {
        return new ResponseEntity<>(utilService.getResponseBody(nftService.getNftById(nftId, true)), HttpStatus.OK);
    }

    /**
     * Create NFT entity.
     *
     * @param image          the image
     * @param walletId       the wallet id
     * @param nftName        the nft name
     * @param nftDescription the nft description
     * @param externalLink   the external link
     * @return the response entity
     * @throws Exception the exception
     */
    @Operation(summary = "Create NFT", description = "Create NFT for user on the specified wallet", tags = {"nft-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "NFT has been created",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = NFTDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> createNFT(final @ApiParam(value = "NFT image") @RequestPart(value = "image") @Valid MultipartFile image,
                                       final @ApiParam(value = "Wallet id") @RequestParam(value = "walletId") Long walletId,
                                       final @ApiParam(value = "NFT name") @RequestParam(value = "name") String nftName,
                                       final @ApiParam(value = "NFT description") @RequestParam(value = "description") String nftDescription,
                                       final @ApiParam(value = "NFT external link") @RequestParam(value = "externalLink", required = false) String externalLink) throws Exception {
        logger.debug("REST request to create a new NFT");
        final NFTDTO nft = new NFTDTO();
        nft.setName(nftName);
        nft.setDescription(nftDescription);
        if (externalLink != null)
            nft.setExternalLink(externalLink);
        nft.setWalletId(walletId);
        final NFTDTO createdNft = nftService.create(nft, userDetailsService.getUser().getId(), image);
        return new ResponseEntity<>(utilService.getResponseBody(createdNft), HttpStatus.CREATED);
    }
}
