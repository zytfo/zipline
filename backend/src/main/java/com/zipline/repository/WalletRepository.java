package com.zipline.repository;

import com.zipline.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Wallet repository contains methods for Wallet entities.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    /**
     * Find all wallets created by a certain user.
     *
     * @param userId the user id
     * @return the list of all wallets created by a certain user
     */
    List<Wallet> findAllByOwnerId(@Param("userId") Long userId);
}
