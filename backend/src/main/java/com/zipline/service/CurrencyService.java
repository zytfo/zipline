package com.zipline.service;

import com.zipline.exception.NoSuchCurrencyException;
import com.zipline.model.Currency;
import com.zipline.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Currency service.
 */
@Service
@Transactional
public class CurrencyService {
    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);
    private final CurrencyRepository currencyRepository;

    /**
     * Instantiates a new Currency service.
     *
     * @param currencyRepository the currency repository
     */
    @Autowired
    public CurrencyService(final CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    /**
     * Save currency.
     *
     * @param currency the currency
     * @return the currency
     */
    public Currency save(final Currency currency) {
        return currencyRepository.save(currency);
    }

    /**
     * Delete by ticker.
     *
     * @param ticker the ticker
     */
    public void deleteByTicker(final String ticker) {
        getCurrencyByTicker(ticker.toUpperCase());
        currencyRepository.deleteByTicker(ticker.toUpperCase());
    }

    /**
     * Gets currency by ticker.
     *
     * @param ticker the ticker
     * @return the currency by ticker
     */
    public Currency getCurrencyByTicker(final String ticker) {
        return currencyRepository.findFirstByTicker(ticker.toUpperCase()).orElseThrow(
                () -> new NoSuchCurrencyException(ticker)
        );
    }

    /**
     * Gets currency by id.
     *
     * @param currencyId the currency id
     * @return the currency by id
     */
    public Optional<Currency> getCurrencyById(final Long currencyId) {
        return currencyRepository.findById(currencyId);
    }

    /**
     * Find all currencies.
     *
     * @return the list of all currencies
     */
    public List<Currency> findAll() {
        return new ArrayList<>(currencyRepository.findAll());
    }
}
