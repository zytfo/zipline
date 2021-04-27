package com.zipline.repository;

import com.zipline.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Confirmation token repository.
 */
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    /**
     * Find by token confirmation token.
     *
     * @param token the token
     * @return the confirmation token
     */
    Optional<ConfirmationToken> findByToken(@Param("token") UUID token);

    /**
     * Delete by token.
     *
     * @param token the token
     */
    void deleteByToken(@Param("token") UUID token);
}
