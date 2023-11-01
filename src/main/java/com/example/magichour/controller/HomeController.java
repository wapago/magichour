package com.example.magichour.controller;

import com.example.magichour.entity.Member;
import com.example.magichour.util.OkHttpUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@RequestMapping
@Log4j2
public class HomeController {
    // TODO
    //   1. 박스오피스 api호출
    //   2.  응답값에서 제목 추출
    //   3. 제목으로 kmdb api 호출
    //   4.  순위, 제목, 연도, 국가, 예매율, 누적관객 dto로 매핑 후 view에 뿌리기

    private final String BOXOFFICE_URL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?targetDt=20231028&key=";
    private final String BOXOFFICE_API_KEY = "977408773efa088487a4cf153953630c";

    private final String KMDB_URL = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&ServiceKey=";
    private final String KMDB_API_KEY = "LZT45SDJ26C2CL3NEF8N";

    @GetMapping("/")
    public String Home(@SessionAttribute(name = "loginMember", required = false) Member member, Model model) {
        // 박스오피스
        String bofJsonString = OkHttpUtils.get(BOXOFFICE_URL + BOXOFFICE_API_KEY);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(bofJsonString);

        JsonObject boxOfficeResult = jsonObject.getAsJsonObject("boxOfficeResult");
        JsonArray dailyBoxOfficeList = boxOfficeResult.getAsJsonArray("dailyBoxOfficeList");

        JsonObject finalData2 = null;

        for(int i=0; i<dailyBoxOfficeList.size(); i++) {
            String movieNm = dailyBoxOfficeList.get(i).getAsJsonObject().get("movieNm").getAsString();
            log.info(movieNm);
            String movieInfo = OkHttpUtils.get(KMDB_URL + KMDB_API_KEY + "&title=" + movieNm);

            JsonObject movieJsonObj = (JsonObject) jsonParser.parse(movieInfo);
            JsonObject movieDataJsonArr = (JsonObject)movieJsonObj.getAsJsonArray("Data").get(0);
            JsonObject finalData = movieDataJsonArr.getAsJsonArray("Result").get(0).getAsJsonObject();

            finalData2.add("title", finalData.get("title"));
            finalData2.add("titleEng", finalData.get("titleEng"));
            finalData2.add("prodYear", finalData.get("prodYear"));
            finalData2.add("nation", finalData.get("nation"));
            finalData2.add("runtime", finalData.get("runtime"));
            finalData2.add("genre", finalData.get("genre"));
            finalData2.add("rating", finalData.get("rating"));
            finalData2.add("posters", finalData.get("posters"));
            finalData2.add("directors", finalData.get("directors"));
            finalData2.add("plots", finalData.get("plots"));
            finalData2.add("actors", finalData.get("actors"));

            log.info(finalData);
        }

        model.addAttribute("loginMember", member);

        return "home";
    }

}
