package com.example.magichour.model.boxoffice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoxOffice {
    private BoxOfficeResult boxOfficeResult;
}
