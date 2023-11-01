package com.example.magichour.model.kmdbsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Actors {
    private List<Actor> actor;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Actor {
        private String actorNm;
        private String actorEnNm;
        private String actorId;
    }
}
