package com.example.magichour.service.movie;

import com.example.magichour.entity.movie.Movie;
import com.example.magichour.util.OkHttpUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class MovieServiceImpl implements MovieService {
    @Value("${box_office.url}")
    private String boxOfficeUrl;

    @Value("${box_office.api_key}")
    private String boxOfficeKey;

    @Value("${kmdb.url}")
    private String kmdbUrl;

    @Value("${kmdb.collection}")
    private String kmdbCollection;

    @Value("${kmdb.detail}")
    private String kmdbDetail;

    @Value("${kmdb.api_key}")
    private String kmdbKey;


    @Override
    public List<Movie> getDailyBoxofficeList() throws JsonProcessingException {
        List<Movie> movieList = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDt = yesterday.format(formatter);

        UriComponents boxofficeUri = UriComponentsBuilder.newInstance()
                .path(boxOfficeUrl)
                .queryParam("targetDt", targetDt)
                .queryParam("key",boxOfficeKey)
                .build();

        String boxofficeResponse = OkHttpUtils.get(boxofficeUri.toString());
        JsonParser jsonParser = new JsonParser();
        JsonObject boxofficeRespToJson = (JsonObject) jsonParser.parse(boxofficeResponse);
        JsonObject boxOfficeResult = boxofficeRespToJson.getAsJsonObject("boxOfficeResult");
        JsonArray dailyBoxOfficeList = boxOfficeResult.getAsJsonArray("dailyBoxOfficeList");

        for(int i=0; i<dailyBoxOfficeList.size(); i++) {
            String movieNm = dailyBoxOfficeList.get(i).getAsJsonObject().get("movieNm").getAsString();
            String audiAcc = dailyBoxOfficeList.get(i).getAsJsonObject().get("audiAcc").getAsString();
            String openDt = dailyBoxOfficeList.get(i).getAsJsonObject().get("openDt").getAsString().replace("-","");
            String rank = dailyBoxOfficeList.get(i).getAsJsonObject().get("rank").getAsString();

            UriComponents kmdbUri = UriComponentsBuilder.newInstance()
                    .path(kmdbUrl)
                    .queryParam("collection", kmdbCollection)
                    .queryParam("detail", kmdbDetail)
                    .queryParam("ServiceKey",kmdbKey)
                    .queryParam("title", movieNm)
                    .queryParam("releaseDts", openDt)
                    .build();

            String kmdbResponse = OkHttpUtils.get(kmdbUri.toString());
            JsonObject kmdbRespToJson = (JsonObject) jsonParser.parse(kmdbResponse);
            JsonObject kmdbData = (JsonObject) kmdbRespToJson.getAsJsonArray("Data").get(0);
            JsonObject kmdbResult = kmdbData.getAsJsonArray("Result").get(0).getAsJsonObject();

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

        return movieList;
    }
}
