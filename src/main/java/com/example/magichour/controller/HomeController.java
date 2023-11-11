package com.example.magichour.controller;

import com.example.magichour.entity.movie.Movie;
import com.example.magichour.util.OkHttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping
@Log4j2
public class HomeController {
    private static final String BOXOFFICE_URL = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?targetDt=20231028&key=";
    private static final String BOXOFFICE_API_KEY = "977408773efa088487a4cf153953630c";

    private static final String KMDB_URL = "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&ServiceKey=";
    private static final String KMDB_API_KEY = "LZT45SDJ26C2CL3NEF8N";

    @GetMapping(value = "/home")
    public ResponseEntity<List<Movie>> Home(HttpSession session) throws JsonProcessingException {
        List<Movie> movieList = new ArrayList<>();

        String boxofficeResponse = OkHttpUtils.get(BOXOFFICE_URL + BOXOFFICE_API_KEY);
        JsonParser jsonParser = new JsonParser();
        JsonObject boxofficeRespToJson = (JsonObject) jsonParser.parse(boxofficeResponse);
        JsonObject boxOfficeResult = boxofficeRespToJson.getAsJsonObject("boxOfficeResult");
        JsonArray dailyBoxOfficeList = boxOfficeResult.getAsJsonArray("dailyBoxOfficeList");

        for(int i=0; i<dailyBoxOfficeList.size(); i++) {
            String movieNm = dailyBoxOfficeList.get(i).getAsJsonObject().get("movieNm").getAsString();
            String audiAcc = dailyBoxOfficeList.get(i).getAsJsonObject().get("audiAcc").getAsString();
            String openDt = dailyBoxOfficeList.get(i).getAsJsonObject().get("openDt").getAsString();
            String rank = dailyBoxOfficeList.get(i).getAsJsonObject().get("rank").getAsString();

            String kmdbResponse = OkHttpUtils.get(KMDB_URL + KMDB_API_KEY + "&title=" + movieNm);
            JsonObject kmdbRespToJson = (JsonObject) jsonParser.parse(kmdbResponse);
            log.info("kmdbRespToJson: " + kmdbRespToJson);
            JsonObject kmdbData = (JsonObject) kmdbRespToJson.getAsJsonArray("Data").get(0);
            JsonObject kmdbResult = kmdbData.getAsJsonArray("Result").get(0).getAsJsonObject();
            log.info("kmdbResult: " + kmdbResult);

            String docId = kmdbResult.get("DOCID").getAsString();
            String posters = kmdbResult.get("posters").getAsString();
            String prodYear = kmdbResult.get("prodYear").getAsString();
            String nation = kmdbResult.get("nation").getAsString();
            String runtime = kmdbResult.get("runtime").getAsString();
            String rating = kmdbResult.get("rating").getAsString();
            String genre = kmdbResult.get("genre").getAsString();
            JsonArray directors = kmdbResult.getAsJsonObject("directors").getAsJsonArray("director");
            JsonArray actors = kmdbResult.getAsJsonObject("actors").getAsJsonArray("actor");
            JsonArray plots = kmdbResult.getAsJsonObject("plots").getAsJsonArray("plot");

            JsonObject movieObject = new JsonObject();

            movieObject.addProperty("movieNm", movieNm);    // 제목
            movieObject.addProperty("audiAcc", audiAcc);    // 누적관객수
            movieObject.addProperty("openDt", openDt);      // 개봉일
            movieObject.addProperty("rank", rank);          // 랭킹
            movieObject.addProperty("docId", docId);
            movieObject.addProperty("posters", posters);    // 포스터 url
            movieObject.addProperty("prodYear", prodYear);
            movieObject.addProperty("nation", nation);
            movieObject.addProperty("runtime", runtime);
            movieObject.addProperty("rating", rating);
            movieObject.addProperty("genre", genre);
            movieObject.add("directors", directors);
            movieObject.add("actors", actors);
            movieObject.add("plots", plots);

            ObjectMapper objectMapper = new ObjectMapper();
            Movie movie = objectMapper.readValue(movieObject.toString(), Movie.class);

            movieList.add(movie);
        }

        return ResponseEntity.ok(movieList);

    }

}
