package com.example.magichour.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MovieEntity {
    @Id
    private String movieId;

    @Column(columnDefinition = "longtext")
    private String movieNm;

    private String genre;
    private String nation;
    private String prodYear;

    @Column(columnDefinition = "longtext")
    private String company;

    @Column(columnDefinition = "longtext")
    private String directors;

    @Column(columnDefinition = "longtext")
    private String actors;

    @Column(columnDefinition = "longtext")
    private String script;

    private String openDt;
    private String runtime;

    @Column(columnDefinition = "longtext")
    private String keyWord;

    @Column(columnDefinition = "longtext")
    private String plots;

    @Column(columnDefinition = "longtext")
    private String registerDate;

    private String modifiedDate;

    public MovieEntity(String movieId, String movieNm, String genre, String nation, String prodYear, String company, String directors, String actors, String script, String openDt, String runtime, String keyWord, String plots, String registerDate, String modifiedDate) {
        this.movieId = movieId;
        this.movieNm = movieNm;
        this.genre = genre;
        this.nation = nation;
        this.prodYear = prodYear;
        this.company = company;
        this.directors = directors;
        this.actors = actors;
        this.script = script;
        this.openDt = openDt;
        this.runtime = runtime;
        this.keyWord = keyWord;
        this.plots = plots;
        this.registerDate = registerDate;
        this.modifiedDate = modifiedDate;
    }
}
