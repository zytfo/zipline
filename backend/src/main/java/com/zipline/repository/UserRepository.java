package com.zipline.repository;

import com.zipline.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The user repository contains methods for user entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by username.
     *
     * @param username the username
     * @return the optional user
     */
    Optional<User> findByUsername(@Param("username") String username);

    /**
     * Find a user by id.
     *
     * @param userId user id
     * @return the optional user
     */
    Optional<User> findById(@Param("userId") Long userId);

    /**
     * Check if user exists by username..
     *
     * @param username the username
     * @return the boolean
     */
    Boolean existsByUsername(@Param("username") String username);

    /**
     * Check if user exists by email.
     *
     * @param email the email
     * @return the boolean
     */
    Boolean existsByEmail(@Param("email") String email);

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<User> findByEmail(@Param("email") String email);
}
