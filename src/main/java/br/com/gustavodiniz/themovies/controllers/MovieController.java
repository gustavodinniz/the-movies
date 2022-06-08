package br.com.gustavodiniz.themovies.controllers;

import br.com.gustavodiniz.themovies.dtos.TheMovieDbApiResponse;
import br.com.gustavodiniz.themovies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @Value("${services.the-movie-db-api.apiKey}")
    private String apiKey;

    @Value("${services.the-movie-db-api.language}")
    private String language;

    private Long page = 1L;

    @GetMapping
    public ResponseEntity<TheMovieDbApiResponse> findTopRated() {

        var movieDbResponse = movieService.findTopRated(apiKey, language, page);
        return ResponseEntity.ok().body(movieDbResponse);
    }
}
