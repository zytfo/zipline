package com.zipline.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

/**
 * The type Publication page.
 */
@Getter
@Setter
public class PublicationPage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "publicationId";
}
