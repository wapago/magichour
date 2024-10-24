package com.example.magichour.controller.movie;

import com.example.magichour.dto.movie.Movie;
import com.example.magichour.entity.MovieEntity;
import com.example.magichour.service.movie.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/magichour")
@Slf4j
public class MovieController {

    private final MovieService movieService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Movie>> boxofficeList() throws JsonProcessingException {
        List<Movie> boxofficeList = movieService.getDailyBoxofficeList();

        return ResponseEntity.ok(boxofficeList);
    }

    @GetMapping(value = "/detail/{movieId}")
    public ResponseEntity<MovieEntity> movieDetail(@PathVariable String movieId) {

        MovieEntity movie = movieService.getMovie(movieId);

        return ResponseEntity.ok(movie);
    }

    @PostMapping(value = "/movie/comment")
    public ResponseEntity<String> commentMovie(Authentication authentication) {
        return ResponseEntity.ok().body(authentication.getName() + "님의 코멘트가 등록되었습니다.");
    }
}
