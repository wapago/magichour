package com.example.magichour.model.kmdbsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ratings {
    private List<Rating> rating;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Rating {
        private String ratingMain;
        private String ratingDate;
        private String ratingNo;
        private String ratingGrade;
        private String releaseDate;
        private String runtime;
    }
}