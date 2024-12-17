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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "longtext")
    private String movieNm;

    private String purpose;

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
}
