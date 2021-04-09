package com.zipline.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * The entity User.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_reading_list",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "news_id"))
    private Set<News> readingList = new HashSet<>();

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "is_account_non_expired")
    private Boolean isAccountNonExpired;

    @Column(name = "is_account_non_locked")
    private Boolean isAccountNonLocked;

    @Column(name = "is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<Wallet> wallets = new HashSet<>();

    /**
     * Instantiates a new User.
     *
     * @param username                the username
     * @param email                   the email
     * @param password                the password
     * @param isEnabled               the is enabled
     * @param isAccountNonExpired     the is account non expired
     * @param isAccountNonLocked      the is account non locked
     * @param isCredentialsNonExpired the is credentials non expired
     */
    public User(final String username,
                final String email,
                final String password,
                final Boolean isEnabled,
                final Boolean isAccountNonExpired,
                final Boolean isAccountNonLocked,
                final Boolean isCredentialsNonExpired) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }
}
