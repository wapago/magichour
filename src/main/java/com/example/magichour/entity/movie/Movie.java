package com.example.magichour.entity.movie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Movie {
    private String movieNm;
    private String audiAcc;
    private String openDt;
    private String rank;
    private String posters;
    private String docId;
    private String prodYear;
    private List<Director> directors;
    private String nation;
    private String runtime;
    private String rating;
    private String genre;
    private List<Actor> actors;
    private List<Plot> plots;

    private static ModelMapper modelMapper = new ModelMapper();

    public static Movie of(String movieObject) {
        return modelMapper.map(movieObject, Movie.class);
    }
}
