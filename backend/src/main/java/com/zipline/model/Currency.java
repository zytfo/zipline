package com.zipline.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The entity currency.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long currencyId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ticker", nullable = false)
    private String ticker;

    @Column(name = "description", nullable = false)
    private String description;
}
