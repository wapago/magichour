package com.example.magichour.entity.movie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Movie {
    private String docId;
    private String movieNm;
    private String audiAcc;
    private String openDt;
    private String rank;
    private String posterUrl;
    private String prodYear;
    private List<Director> directors;
    private String nation;
    private String runtime;
    private String rating;
    private String genre;
    private List<Actor> actors;
    private List<Plot> plots;
}
