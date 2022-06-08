package br.com.gustavodiniz.themovies.services.impl;

import br.com.gustavodiniz.themovies.clients.MovieClient;
import br.com.gustavodiniz.themovies.dtos.TheMovieDbApiResponse;
import br.com.gustavodiniz.themovies.models.MovieModel;
import br.com.gustavodiniz.themovies.repositories.MovieRepository;
import br.com.gustavodiniz.themovies.services.MovieService;
import br.com.gustavodiniz.themovies.services.exceptions.ErrorException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieClient movieClient;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TheMovieDbApiResponse findTopRated(String apiKey, String language, Long page) {
        var response = movieClient.findTopRated(apiKey, language, page).orElseThrow(() -> new ErrorException("Unable to complete the request"));
        List<MovieModel> movies = response.getResults().stream().map(x -> modelMapper.map(x, MovieModel.class)).collect(Collectors.toList());
        movieRepository.saveAll(movies);
        return response;
    }
}
