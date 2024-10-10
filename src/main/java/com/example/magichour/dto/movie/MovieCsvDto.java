package com.example.magichour.dto.movie;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class MovieCsvDto {
    private String registerId;      // 셀번호0 : 영화등록번호ID(K, F)
    private String registerNumber;  // 셀번호1 : 영화등록번호NO
    private String title;           // 셀번호2 : 영화명
    private String englishTitle;    // 셀번호3 : 영문영화명
    private String originalTitle;   // 셀번호4 : 원제명
    private String type;            // 셀번호5 : 유형(극영화)
    private String purpose;         // 셀번호6 : 용도(극장용)
    private String genre;           // 셀번호7 : 장르
    private String nation;          // 셀번호8 : 제작국가
    private String year;            // 셀번호9 : 제작연도
    private String company;         // 셀번호10 : 제작사
    private String director;        // 셀번호11 : 감독
    private String starring;        // 출연
    private String script;          // 각본
    private String releaseDate;     // 개봉일
    private String runningTime;     // 상영시간
    private String keyWord;         // 키워드
    private String plot;            // 줄거리
    private String registerDate;    // 최초등록일
    private String modifiedDate;    // 최종수정일

    public static List<String> getFieldNames() {
        Field[] declaredFields = MovieCsvDto.class.getDeclaredFields();
        List<String> result = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            result.add(declaredField.getName());
        }

        for(String fieldName : result) {
            log.info(fieldName);
        }

        return result;
    }
}
