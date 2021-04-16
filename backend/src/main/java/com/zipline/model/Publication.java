package com.zipline.model;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The entity Publication.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "publications")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long publicationId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "content")
    private String content;

    @Type(type = "list-array")
    @Column(name = "tickers", columnDefinition = "varchar[]")
    private List<String> tickers;

    @Type(type = "list-array")
    @Column(name = "trades", columnDefinition = "bigint[]")
    private List<Long> tradeIds;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;
}
