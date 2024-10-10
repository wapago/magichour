package com.example.magichour.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registerId;      // 셀번호0 : 영화등록번호ID(K, F)
    private String registerNumber;  // 셀번호1 : 영화등록번호NO

    @Column(columnDefinition = "longtext")
    private String title;           // 셀번호2 : 영화명

    @Column(columnDefinition = "longtext")
    private String englishTitle;    // 셀번호3 : 영문영화명

    @Column(columnDefinition = "longtext")
    private String originalTitle;   // 셀번호4 : 원제명

    @Column(columnDefinition = "longtext")
    private String type;            // 셀번호5 : 유형(극영화)
    private String purpose;         // 셀번호6 : 용도(극장용)
    private String genre;           // 셀번호7 : 장르
    private String nation;          // 셀번호8 : 제작국가
    private String year;            // 셀번호9 : 제작연도

    @Column(columnDefinition = "longtext")
    private String company;         // 셀번호10 : 제작사

    @Column(columnDefinition = "longtext")
    private String director;        // 셀번호11 : 감독

    @Column(columnDefinition = "longtext")
    private String starring;        // 출연

    @Column(columnDefinition = "longtext")
    private String script;          // 각본

    private String releaseDate;     // 개봉일
    private String runningTime;     // 상영시간

    @Column(columnDefinition = "longtext")
    private String keyWord;         // 키워드

    @Column(columnDefinition = "longtext")
    private String plot;            // 줄거리

    @Column(columnDefinition = "longtext")
    private String registerDate;    // 최초등록일

    private String modifiedDate;    // 최종수정일
    private String anonymous;
}
