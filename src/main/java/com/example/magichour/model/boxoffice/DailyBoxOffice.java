package com.example.magichour.model.boxoffice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyBoxOffice {
    private String rank;            // 순위
    private String movieNm;         // 영화이름(국문)
    private String openDt;          // 개봉일
    private String audiCnt;         // 해당일 관객수
    private String audiAcc;         // 누적 관객수
}
