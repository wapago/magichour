package com.example.magichour.controller;

import com.example.magichour.entity.Member;
import com.example.magichour.model.boxoffice.BoxOffice;
import com.example.magichour.util.OkHttpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping
@Log4j2
public class HomeController {
    private final String BOXOFFICE_URL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?targetDt=20231021&key=";
    private final String BOXOFFICE_API_KEY = "977408773efa088487a4cf153953630c";

    @GetMapping("/")
    public String Home(@SessionAttribute(name = "loginMember", required = false) Member member, Model model, HttpSession session) {
        String jsonData = OkHttpUtils.get(BOXOFFICE_URL + BOXOFFICE_API_KEY);
        log.info(jsonData);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BoxOffice boxOffice = objectMapper.readValue(jsonData, BoxOffice.class);

            model.addAttribute("loginMember", member);
            model.addAttribute("boxoffice", boxOffice);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "home";
    }
}
