package com.example.magichour.model.boxoffice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoxOfficeResult {
    private String boxofficeType;
    private String showRange;
    private List<DailyBoxOffice> dailyBoxOfficeList;
}
