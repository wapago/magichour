package com.example.magichour.model.kmdbsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {
    private String Query;
    private String KMAQuery;
    private int TotalCount;
    private List<Data> Data;
}






