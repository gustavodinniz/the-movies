package br.com.gustavodiniz.themovies.services;

import br.com.gustavodiniz.themovies.dtos.TheMovieDbApiResponse;
import br.com.gustavodiniz.themovies.models.MovieModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    TheMovieDbApiResponse findTopRated(String apiKey, String language, Long page);

    Page<MovieModel> findAll(Pageable pageable);

    MovieModel findById(Long id);

   Page<MovieModel> findSuggestionsByWeather(String city, Pageable pageable);
}
