package com.example.magichour.model.kmdbsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Staffs {
    private List<Staff> staff;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Staff {
        private String staffNm;
        private String staffEnNm;
        private String staffRoleGroup;
        private String staffRole;
        private String staffEtc;
        private String staffId;
    }
}
