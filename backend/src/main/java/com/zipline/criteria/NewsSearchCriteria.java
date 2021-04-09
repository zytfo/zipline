package com.zipline.criteria;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type News search criteria.
 */
@Getter
@Setter
public class NewsSearchCriteria {
    private String title;
    private String description;
    private String content;
    private String source;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created;
    private List<String> tags;
}
