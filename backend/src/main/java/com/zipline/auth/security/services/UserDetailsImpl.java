package com.zipline.auth.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zipline.model.News;
import com.zipline.model.User;
import com.zipline.model.Wallet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type User details.
 */
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private final Long id;
    private final String username;
    @JsonIgnore
    private final String email;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Collection<News> markedNews;
    @JsonIgnore
    private Boolean isEnabled;
    @JsonIgnore
    private Boolean isAccountNonExpired;
    @JsonIgnore
    private Boolean isAccountNonLocked;
    @JsonIgnore
    private Boolean isCredentialsNonExpired;
    private final Collection<Long> wallets;

    /**
     * Instantiates a new User details.
     *
     * @param id                      the id
     * @param username                the username
     * @param email                   the email
     * @param password                the password
     * @param authorities             the authorities
     * @param markedNews              the marked news
     * @param isEnabled               the is enabled
     * @param isAccountNonExpired     the is account non expired
     * @param isAccountNonLocked      the is account non locked
     * @param isCredentialsNonExpired the is credentials non expired
     * @param wallets                 the wallets IDs
     */
    public UserDetailsImpl(final Long id,
                           final String username,
                           final String email,
                           final String password,
                           final Collection<? extends GrantedAuthority> authorities,
                           final Collection<News> markedNews,
                           final Boolean isEnabled,
                           final Boolean isAccountNonExpired,
                           final Boolean isAccountNonLocked,
                           final Boolean isCredentialsNonExpired,
                           final Collection<Long> wallets) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.markedNews = markedNews;
        this.isEnabled = isEnabled;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.wallets = wallets;
    }

    /**
     * Build user details.
     *
     * @param user the user
     * @return the user details
     */
    public static UserDetailsImpl build(final User user) {
        final List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        final List<News> markedNews = new ArrayList<>(user.getReadingList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                markedNews,
                user.getIsEnabled(),
                user.getIsAccountNonExpired(),
                user.getIsAccountNonLocked(),
                user.getIsAccountNonExpired(),
                user.getWallets().stream().map(Wallet::getWalletId).collect(Collectors.toSet()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
