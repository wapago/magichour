package com.example.magichour.model.kmdbsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vods {
    private List<Vod> vod;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Vod {
        private String vodClass;
        private String vodUrl;
    }
}
