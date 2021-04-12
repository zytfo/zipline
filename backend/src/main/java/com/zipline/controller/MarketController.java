package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.MarketTradeDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.service.MarketService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * The type Market controller
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/market")
@Tag(name = "market-controller", description = "Market API")
public class MarketController {
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final UtilService utilService;
    private final MarketService marketService;

    /**
     * Instantiates a new instance of Market Controller
     *
     * @param modelMapper        the modelMapper
     * @param userDetailsService the userDetailsService
     * @param utilService        the utilService
     * @param marketService      the marketService
     */
    @Autowired
    public MarketController(ModelMapper modelMapper, UserDetailsServiceImpl userDetailsService, UtilService utilService, MarketService marketService) {
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.utilService = utilService;
        this.marketService = marketService;
    }

    @Operation(summary = "Get all open trades", description = "Get all open trades", tags = {"market-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MarketTradeDTO.class))))})
    @GetMapping("/trades/all_open")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getOpenTrades() {
        return new ResponseEntity<>(utilService.getResponseBody(marketService.getAllOpenTrades()), HttpStatus.OK);
    }

    @Operation(summary = "Get trade by ID", description = "Get a particular trade by its ID", tags = {"market-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MarketTradeDTO.class))))})
    @GetMapping("/trades/{tradeId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getTradeById(@PathVariable BigInteger tradeId) {
        return new ResponseEntity<>(utilService.getResponseBody(marketService.getTradeById(tradeId)), HttpStatus.OK);
    }

    @Operation(summary = "Get trades of the user", description = "Get all trades of the particular user", tags = {"market-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MarketTradeDTO.class))))})
    @GetMapping("/trades/of_user")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getTradesOfUser() {
        return new ResponseEntity<>(utilService.getResponseBody(marketService.getAllTradesOfUser(userDetailsService.getUser().getId())), HttpStatus.OK);
    }

    @Operation(summary = "Open a new trade", description = "Open a new trade", tags = {"market-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MarketTradeDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @PostMapping(value = "/trades/open", consumes = "application/json")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> openTrade(final @ApiParam(value = "New trade") @RequestBody MarketTradeDTO tradeDTO) throws Exception {
        return new ResponseEntity<>(utilService.getResponseBody(marketService.openTrade(userDetailsService.getUser().getId(), tradeDTO)), HttpStatus.OK);
    }

    @Operation(summary = "Execute trade", description = "Execute the trade buying an NFT", tags = {"market-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MarketTradeDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @PostMapping(value = "/trades/{tradeId}/execute/{walletId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> executeTrade(@PathVariable BigInteger tradeId, @PathVariable Long walletId) throws Exception {
        marketService.executeTrade(userDetailsService.getUser().getId(), tradeId, walletId);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }

    @Operation(summary = "Cancel trade", description = "Cancel the trade posted by the user", tags = {"market-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MarketTradeDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
    })
    @DeleteMapping(value = "/trades/{tradeId}/cancel")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> executeTrade(@PathVariable BigInteger tradeId) throws Exception {
        marketService.cancelTrade(userDetailsService.getUser().getId(), tradeId);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }
}
