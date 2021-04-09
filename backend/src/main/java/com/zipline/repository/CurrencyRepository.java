package com.zipline.repository;

import com.zipline.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The currency repository contains methods for currencies entities.
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    /**
     * Find currency by ticker.
     *
     * @param ticker the ticker
     * @return the currency entity
     */
    Optional<Currency> findFirstByTicker(@Param("ticker") String ticker);

    /**
     * Delete by ticker.
     *
     * @param ticker the ticker
     */
    void deleteByTicker(@Param("ticker") String ticker);

    /**
     * Exists by ticker.
     *
     * @param ticker the ticker
     * @return the boolean
     */
    Boolean existsByTicker(@Param("ticker") String ticker);
}
