package com.example.magichour.controller;

import com.example.magichour.entity.Member;
import com.example.magichour.util.OkHttpUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
        JsonObject movieObject = new JsonObject();

        // 박스오피스
        String boxofficeResponse = OkHttpUtils.get(BOXOFFICE_URL + BOXOFFICE_API_KEY);

        JsonParser jsonParser = new JsonParser();
        JsonObject boxofficeRespToJson = (JsonObject) jsonParser.parse(boxofficeResponse);
        JsonObject boxOfficeResult = boxofficeRespToJson.getAsJsonObject("boxOfficeResult");
        JsonArray dailyBoxOfficeList = boxOfficeResult.getAsJsonArray("dailyBoxOfficeList");
        log.info(dailyBoxOfficeList);


        for(int i=0; i<dailyBoxOfficeList.size(); i++) {
            JsonElement movieNm = dailyBoxOfficeList.get(i).getAsJsonObject().get("movieNm");
            JsonElement audiAcc = dailyBoxOfficeList.get(i).getAsJsonObject().get("audiAcc");
            JsonElement openDt = dailyBoxOfficeList.get(i).getAsJsonObject().get("openDt");
            JsonElement rank = dailyBoxOfficeList.get(i).getAsJsonObject().get("rank");

            String kmdbResponse = OkHttpUtils.get(KMDB_URL + KMDB_API_KEY + "&title=" + movieNm.getAsString());
            JsonObject kmdbRespToJson = (JsonObject) jsonParser.parse(kmdbResponse);
            JsonObject kmdbData = (JsonObject) kmdbRespToJson.getAsJsonArray("Data").get(0);
            JsonObject kmdbResult = kmdbData.getAsJsonArray("Result").get(0).getAsJsonObject();
            JsonElement posters = kmdbResult.get("posters");
            JsonElement prodYear = kmdbResult.get("prodYear");
            JsonElement directors = kmdbResult.get("directors");
            JsonElement nation = kmdbResult.get("nation");
            JsonElement runtime = kmdbResult.get("runtime");
            JsonElement rating = kmdbResult.get("rating");
            JsonElement genre = kmdbResult.get("genre");
            JsonElement actors = kmdbResult.get("actors");
            JsonElement plots = kmdbResult.get("plots");

            movieObject.add("movieNm", movieNm);    // 제목
            movieObject.add("audiAcc", audiAcc);    // 누적관객수
            movieObject.add("openDt", openDt);      // 개봉일
            movieObject.add("rank", rank);          // 랭킹
            movieObject.add("posters", posters);    // 포스터 url
            movieObject.add("prodYear", prodYear);
            movieObject.add("directors", directors);
            movieObject.add("nation", nation);
            movieObject.add("runtime", runtime);
            movieObject.add("rating", rating);
            movieObject.add("genre", genre);
            movieObject.add("actors", actors);
            movieObject.add("plots", plots);

            log.info(movieObject);
        }

        model.addAttribute("loginMember", member);

        return "home";
    }

}
