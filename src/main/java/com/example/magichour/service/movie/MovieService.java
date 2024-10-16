package com.example.magichour.service.movie;

import com.example.magichour.dto.movie.Movie;
import com.example.magichour.entity.MovieEntity;
import com.example.magichour.repository.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MovieService {
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

    private final MovieRepository movieRepository;

    public List<Movie> getDailyBoxofficeList() throws JsonProcessingException {
        List<Movie> movieList = new ArrayList<>();

        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String targetDt = yesterday.format(formatter);

        WebClient boxofficeWebClient = WebClient.builder()
                .baseUrl(boxOfficeUrl)
                .build();

        String boxofficeResponse = boxofficeWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("targetDt", targetDt)
                        .queryParam("key", boxOfficeKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("=============== BOXOFFICE RESPONSE ===============");
        log.info(boxofficeResponse);

        JsonParser jsonParser = new JsonParser();
        JsonObject boxofficeRespToJson = (JsonObject) jsonParser.parse(boxofficeResponse);
        JsonObject boxOfficeResult = boxofficeRespToJson.getAsJsonObject("boxOfficeResult");
        JsonArray dailyBoxOfficeList = boxOfficeResult.getAsJsonArray("dailyBoxOfficeList");

        WebClient kmdbWebClient = WebClient.builder()
                .baseUrl(kmdbUrl)
                .build();

        for(int i=0; i<dailyBoxOfficeList.size(); i++) {
            String movieNm = dailyBoxOfficeList.get(i).getAsJsonObject().get("movieNm").getAsString();
            String audiAcc = dailyBoxOfficeList.get(i).getAsJsonObject().get("audiAcc").getAsString();
            String openDt = dailyBoxOfficeList.get(i).getAsJsonObject().get("openDt").getAsString().replace("-","");
            String rank = dailyBoxOfficeList.get(i).getAsJsonObject().get("rank").getAsString();

            String kmdbResponse = kmdbWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("collection", kmdbCollection)
                            .queryParam("detail", kmdbDetail)
                            .queryParam("ServiceKey",kmdbKey)
                            .queryParam("title", movieNm)
                            .queryParam("releaseDts", openDt)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("=============== KMDB RESPONSE ===============");
            log.info(kmdbResponse);

            JsonObject kmdbRespToJson = (JsonObject) jsonParser.parse(kmdbResponse);
            JsonObject kmdbData = (JsonObject) kmdbRespToJson.getAsJsonArray("Data").get(0);
            JsonObject kmdbResult = kmdbData.getAsJsonArray("Result").get(0).getAsJsonObject();

            String movieId = kmdbResult.get("DOCID").getAsString();
            String posterUrl = kmdbResult.get("posters").getAsString().split("\\|")[0];
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
            movieObject.addProperty("movieId", movieId);
            movieObject.addProperty("posterUrl", posterUrl);    // 포스터 url
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

    public MovieEntity getMovie(String movieId) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow();

        return movie;
    }
}
