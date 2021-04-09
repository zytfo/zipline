package com.zipline.auth.security.services;

import com.zipline.exception.NoSuchTokenException;
import com.zipline.exception.NoSuchUserException;
import com.zipline.model.ConfirmationToken;
import com.zipline.model.News;
import com.zipline.model.User;
import com.zipline.repository.ConfirmationTokenRepository;
import com.zipline.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * The type User details service.
 */
@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    /**
     * Instantiates a new User details service.
     *
     * @param userRepository              the user repository
     * @param confirmationTokenRepository the confirmation token repository
     */
    public UserDetailsServiceImpl(final UserRepository userRepository,
                                  final ConfirmationTokenRepository confirmationTokenRepository) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    /**
     * Load user by user id.
     *
     * @param userId the user id
     * @return the user details
     */
    public UserDetails loadUserByUserId(final Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(userId));
        return UserDetailsImpl.build(user);
    }

    /**
     * Gets user.
     *
     * @return the user details.
     */
    public UserDetailsImpl getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) loadUserByUsername(authentication.getName());
    }

    /**
     * Load user by token user details.
     *
     * @param token the token
     * @return the user details
     */
    public User loadUserByToken(final UUID token) {
        final Optional<ConfirmationToken> confirmationToken = confirmationTokenRepository.findByToken(token);
        if (!confirmationToken.isPresent()) {
            throw new NoSuchTokenException(token.toString());
        }
        return userRepository.findById(confirmationToken.get().getUser().getId()).orElseThrow(
                () -> new NoSuchUserException(confirmationToken.get().getUser().getId())
        );
    }

    /**
     * Delete by token.
     *
     * @param token the token
     */
    public void deleteByToken(final UUID token) {
        confirmationTokenRepository.deleteByToken(token);
    }

    /**
     * Save marked news user details.
     *
     * @param userId     the user id
     * @param markedNews the marked news
     */
    public void saveMarkedNews(final Long userId, final Set<News> markedNews) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(userId));
        user.setReadingList(markedNews);
        userRepository.save(user);
    }

    /**
     * Save confirmation token.
     *
     * @param token the token
     */
    public void saveConfirmationToken(final ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }
}
