package com.example.magichour.service.movie;

import com.example.magichour.entity.movie.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface MovieService {
    List<Movie> getDailyBoxofficeList() throws JsonProcessingException;
}
