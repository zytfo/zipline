package com.zipline.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Confirmation token.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "confirmation_tokens")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private UUID token;

    @Column(name = "created")
    private LocalDateTime created;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    /**
     * Instantiates a new Confirmation token.
     *
     * @param token the token
     * @param user  the user
     */
    public ConfirmationToken(final UUID token, final User user) {
        this.token = token;
        this.created = LocalDateTime.now();
        this.user = user;
    }
}
