package com.zipline.controller;

import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.CurrencyDTO;
import com.zipline.exception.ErrorResponse;
import com.zipline.model.Currency;
import com.zipline.service.CoinPaprikaClientService;
import com.zipline.service.CurrencyService;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * The currency controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/currency")
@Tag(name = "currencies-controller", description = "Currencies API")
public class CurrencyController {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private final CurrencyService currencyService;
    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final CoinPaprikaClientService coinPaprikaClientService;
    private final UtilService utilService;

    /**
     * Instantiates a new Currency controller.
     *
     * @param currencyService          the currency service
     * @param modelMapper              the model mapper
     * @param userDetailsService       the user details service
     * @param coinPaprikaClientService the coin paprika client service
     * @param utilService              the util service
     */
    public CurrencyController(final CurrencyService currencyService,
                              final ModelMapper modelMapper,
                              final UserDetailsServiceImpl userDetailsService,
                              final CoinPaprikaClientService coinPaprikaClientService,
                              final UtilService utilService) {
        this.currencyService = currencyService;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.coinPaprikaClientService = coinPaprikaClientService;
        this.utilService = utilService;
    }

    /**
     * Gets all currencies.
     *
     * @return the all currencies
     */
    @Operation(summary = "Find all currencies",
            description = "Get all currencies by user, moderator or admin", tags = {"currencies-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CurrencyDTO.class))))})
    @GetMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllCurrencies() {
        logger.debug("REST request to get all currencies");
        final List<CurrencyDTO> currencyDTOs = new ArrayList<>();
        for (Currency currency : currencyService.findAll())
            currencyDTOs.add(modelMapper.map(currency, CurrencyDTO.class));
        return new ResponseEntity<>(utilService.getResponseBody(currencyDTOs), HttpStatus.OK);
    }

    /**
     * Gets currency by ticker.
     *
     * @param ticker the ticker
     * @return the currency
     */
    @Operation(summary = "Get currency by ticker", description = "Get currency by ticker by user, moderator or admin", tags = {"currencies-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CurrencyDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Currency not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @GetMapping(value = "/{ticker}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getCurrencyByTicker(final @ApiParam(value = "Ticker to get information about") @PathVariable("ticker") String ticker) {
        logger.debug("REST request to get currency by ticker {}", ticker);
//        coinPaprikaClientService.getTickerByCoinId(ticker);
        return new ResponseEntity<>(utilService.getResponseBody(modelMapper.map(currencyService.getCurrencyByTicker(ticker), CurrencyDTO.class)), HttpStatus.OK);
    }

    /**
     * Create currency response entity.
     *
     * @param currencyDTO the currency dto
     * @return the response entity
     */
    @Operation(summary = "Create currency", description = "Create currency by moderator or admin", tags = {"currencies-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Currency has been created",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CurrencyDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createCurrency(final @ApiParam(value = "New currency") @Valid @RequestBody CurrencyDTO currencyDTO) {
        logger.debug("REST request to create a new currency {}", currencyDTO);
        currencyDTO.setTicker(currencyDTO.getTicker().toUpperCase());
        final Currency currency = currencyService.save(modelMapper.map(currencyDTO, Currency.class));
        return new ResponseEntity<>(utilService.getResponseBody(modelMapper.map(currency, CurrencyDTO.class)), HttpStatus.CREATED);
    }

    /**
     * Update currency response entity.
     *
     * @param currencyDTO the currency dto
     * @param ticker      the ticker
     * @return the response entity
     */
    @Operation(summary = "Update currency", description = "Update currency by ticker by moderator or admin", tags = {"currencies-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Currency has been updated",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CurrencyDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Currency was not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PutMapping(value = "/{ticker}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateCurrency(
            final @ApiParam(value = "Body of a new currency") @Valid @RequestBody CurrencyDTO currencyDTO,
            final @ApiParam(value = "Ticker to update") @PathVariable(value = "ticker") String ticker) {
        logger.debug("REST request to update an existing currency {}", currencyDTO);
        final Currency loadedCurrency = currencyService.getCurrencyByTicker(ticker);
        loadedCurrency.setDescription(currencyDTO.getDescription());
        final Currency currency = currencyService.save(loadedCurrency);
        return new ResponseEntity<>(utilService.getResponseBody(modelMapper.map(currency, CurrencyDTO.class)), HttpStatus.ACCEPTED);
    }

    /**
     * Delete currency by ticker response entity.
     *
     * @param ticker the ticker
     * @return the response entity
     */
    @Operation(summary = "Delete currency", description = "Delete currency by ticker by moderator or admin", tags = {"currencies-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful delete",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CurrencyDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Currency was not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @DeleteMapping(value = "/delete/{ticker}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteCurrencyByTicker(final @ApiParam(value = "Ticker to delete currency") @PathVariable(value = "ticker") String ticker) {
        logger.debug("REST request to delete an existing currency by ticker {}", ticker);
        currencyService.deleteByTicker(ticker);
        return new ResponseEntity<>(utilService.getEmptyResponseBody(), HttpStatus.OK);
    }
}
