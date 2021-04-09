package com.zipline.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The type Complaint.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complaints")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long complaintId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "post_type", nullable = false)
    private PostType postType;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "post_author", nullable = false)
    private Long postAuthorId;

    @Column(name = "reason", nullable = false)
    private ReasonType reason;

    @Column(name = "message")
    private String message;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

}
