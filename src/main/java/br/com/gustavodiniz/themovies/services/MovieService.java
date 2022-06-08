package br.com.gustavodiniz.themovies.services;

import br.com.gustavodiniz.themovies.dtos.MovieResponse;
import br.com.gustavodiniz.themovies.dtos.TheMovieDbApiResponse;
import br.com.gustavodiniz.themovies.models.MovieModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

public interface MovieService {
    TheMovieDbApiResponse findTopRated(String apiKey, String language, Long page);

    Page<MovieModel> findAll(Pageable pageable);

    MovieModel findById(Long id);
}
