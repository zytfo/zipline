package com.zipline.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zipline.model.PostType;
import com.zipline.model.ReasonType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
public class ComplaintDTO implements Serializable {
    private static final long serialVersionUID = -3256323814080499022L;

    @JsonIgnore
    private Long complaintId;

    @JsonIgnore
    private Long userId;

    @NotEmpty(message = "Post type must not be empty")
    private PostType postType;

    @NotEmpty(message = "Post id must not be empty")
    private Long postId;

    @JsonIgnore
    private Long postAuthorId;

    @NotEmpty(message = "Reason must not be empty")
    private ReasonType reason;
    private String message;

    @JsonIgnore
    private LocalDateTime created;
}
