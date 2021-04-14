package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsImpl;
import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.NFTDTO;
import com.zipline.dto.WalletDTO;
import com.zipline.dto.WalletImportExportDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.model.NFT;
import com.zipline.model.Wallet;
import com.zipline.service.NFTService;
import com.zipline.service.UtilService;
import com.zipline.service.WalletService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Wallet controller
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/wallets")
@Tag(name = "wallet-controller", description = "Wallet API")
public class WalletController {
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final UtilService utilService;
    private final WalletService walletService;
    private final NFTService nftService;

    /**
     * Instantiates a new Wallet controller.
     *
     * @param modelMapper        the model mapper
     * @param userDetailsService the user details service
     * @param utilService        the util service
     * @param walletService      the Wallet service
     * @param nftService         the NFT service
     */
    @Autowired
    public WalletController(final ModelMapper modelMapper,
                            final UserDetailsServiceImpl userDetailsService,
                            final UtilService utilService,
                            final WalletService walletService, NFTService nftService) {
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.utilService = utilService;
        this.walletService = walletService;
        this.nftService = nftService;
    }

    @Operation(summary = "Get Wallets", description = "Get all wallets of the user", tags = {"wallet-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WalletDTO.class))))})
    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getWallets() {
        logger.debug("REST request to get all Wallets of the user");
        final List<WalletDTO> walletDTOS = new ArrayList<>();
        final UserDetailsImpl userDetails = userDetailsService.getUser();
        final List<Wallet> wallets = walletService.getWalletsOfUser(userDetails.getId());
        for (Wallet wallet : wallets) {
            WalletDTO walletDTO = modelMapper.map(wallet, WalletDTO.class);
            walletDTO.setNftIds(nftService
                    .getNftsByWalletId(wallet.getWalletId()).stream()
                    .map(NFTDTO::getNftId)
                    .collect(Collectors.toSet()));
            walletDTOS.add(walletDTO);
        }
        return new ResponseEntity<>(utilService.getResponseBody(walletDTOS), HttpStatus.OK);
    }

    @Operation(summary = "Create Wallet", description = "Create a new Wallet", tags = {"wallet-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WalletDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> createWallet(final @ApiParam(value = "Name of the new wallet") @RequestParam String walletName) throws Exception {
        logger.debug("REST request to create a new Wallet");
        Wallet newWallet = walletService.createWallet(userDetailsService.getUser().getId(), walletName);
        return new ResponseEntity<>(utilService.getResponseBody(modelMapper.map(newWallet, WalletDTO.class)), HttpStatus.OK);
    }

    @Operation(summary = "Delete Wallet", description = "Delete Wallet from the user by ID", tags = {"wallet-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WalletDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @DeleteMapping("/{walletId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> deleteWallet(@PathVariable Long walletId) throws Exception {
        logger.debug("REST request to delete a Wallet");
        walletService.deleteWallet(userDetailsService.getUser().getId(), walletId);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }

    @Operation(summary = "Import Wallet", description = "Import a Wallet", tags = {"wallet-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WalletDTO.class))))})
    @PostMapping("/import")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> importWallet(final @ApiParam(value = "New wallet") @RequestBody WalletImportExportDTO wallet) throws Exception {
        logger.debug("REST request to import a Wallet");
        Wallet importedWallet = walletService.importWallet(
                userDetailsService.getUser().getId(), wallet.getWalletName(), wallet.getPrivateKey());
        WalletDTO walletDTO = modelMapper.map(importedWallet, WalletDTO.class);
        walletDTO.setNftIds(nftService
                .getNftsByWalletId(walletDTO.getWalletId()).stream()
                .map(NFTDTO::getNftId)
                .collect(Collectors.toSet()));
        return new ResponseEntity<>(utilService.getResponseBody(walletDTO), HttpStatus.OK);
    }

    @Operation(summary = "Export Wallet", description = "Export a Wallet by ID", tags = {"wallet-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WalletDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping("/{walletId}/export/")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> exportWallet(@PathVariable Long walletId) throws Exception {
        logger.debug("REST request to export a Wallet");
        WalletImportExportDTO walletDTO = new WalletImportExportDTO();
        walletDTO.setWalletId(walletId);
        walletDTO.setPrivateKey(walletService.exportWallet(userDetailsService.getUser().getId(), walletId));
        return new ResponseEntity<>(utilService.getResponseBody(walletDTO), HttpStatus.OK);
    }

    @Operation(summary = "Get Wallet Balance", description = "Get balance of the wallet of the user", tags = {"wallet-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BigInteger.class))))})
    @GetMapping("/{walletId}/balance")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getWallets(@PathVariable Long walletId) throws NotFoundException, IOException {
        logger.debug("REST request to get balance of the Wallet of the user");
        BigInteger balance = walletService.getWalletBalance(userDetailsService.getUser().getId(), walletId);
        return new ResponseEntity<>(utilService.getResponseBody(balance), HttpStatus.OK);
    }

    @Operation(summary = "Get Wallet Withdrawals", description = "Get pending withdrawals of the wallet", tags = {"wallet-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BigInteger.class))))})
    @GetMapping("/{walletId}/withdrawals")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getWalletWithdrawals(@PathVariable Long walletId) throws Exception {
        logger.debug("REST request to get pending withdrawals of the Wallet of the user");
        BigInteger withdrawals = walletService.getWalletPendingWithdrawals(userDetailsService.getUser().getId(), walletId);
        return new ResponseEntity<>(utilService.getResponseBody(withdrawals), HttpStatus.OK);
    }

    @Operation(summary = "Withdraw", description = "Withdraw the pending withdrawals", tags = {"wallet-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BigInteger.class))))})
    @PostMapping("/{walletId}/withdrawals")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> withdraw(@PathVariable Long walletId) throws Exception {
        logger.debug("REST request to get pending withdrawals of the Wallet of the user");
        walletService.withdrawWallet(userDetailsService.getUser().getId(), walletId);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }
}
