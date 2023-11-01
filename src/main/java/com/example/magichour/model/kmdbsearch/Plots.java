package com.example.magichour.model.kmdbsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plots {
    private List<Plot> plot;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Plot {
        private String plotLang;
        private String plotText;
    }
}
