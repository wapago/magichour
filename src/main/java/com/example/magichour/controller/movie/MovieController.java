package com.example.magichour.controller.movie;

import com.example.magichour.entity.movie.Movie;
import com.example.magichour.service.movie.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@Log4j2
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
            this.movieService = movieService;
    }

    @GetMapping(value = "/home")
    public ResponseEntity<List<Movie>> BoxofficeList(HttpSession session) throws JsonProcessingException {
        List<Movie> boxofficeList = movieService.getDailyBoxofficeList();

        return ResponseEntity.ok(boxofficeList);
    }

}
