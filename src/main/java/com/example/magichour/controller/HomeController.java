package com.example.magichour.controller;

import com.example.magichour.util.OkHttpUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@Log4j2
public class HomeController {
    private final String BOXOFFICE_URL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?targetDt=20231021&key=";
    private final String BOXOFFICE_API_KEY = "977408773efa088487a4cf153953630c";

    @GetMapping("/")
    public String Home(Model model) {
        String boxoffice = OkHttpUtils.get(BOXOFFICE_URL + BOXOFFICE_API_KEY);

        log.info(boxoffice);

        return "/home";
    }
}
