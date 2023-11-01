package com.example.magichour.model.kmdbsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private String CollName;
    private int TotalCount;
    private int Count;
    private List<Result> Result;
}