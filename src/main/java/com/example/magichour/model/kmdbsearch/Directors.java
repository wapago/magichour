package com.example.magichour.model.kmdbsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Directors {
    private List<Director> director;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Director {
        private String directorNm;
        private String directorEnNm;
        private String directorId;

    }
}

