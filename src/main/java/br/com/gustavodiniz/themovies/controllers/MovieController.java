package br.com.gustavodiniz.themovies.controllers;

import br.com.gustavodiniz.themovies.TheMoviesApplication;
import br.com.gustavodiniz.themovies.clients.MovieClient;
import br.com.gustavodiniz.themovies.dtos.TheMovieDbApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieClient movieClient;

    @Value("${services.the-movie-db-api.apiKey}")
    private String apiKey;

    @Value("${services.the-movie-db-api.language}")
    private String language;

    private Long page =  1L;

    @GetMapping
    public TheMovieDbApiResponse findTopRated(){
        return movieClient.findTopRated(apiKey, language, page).orElse(new TheMovieDbApiResponse());
    }
}
