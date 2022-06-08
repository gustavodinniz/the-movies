package br.com.gustavodiniz.themovies.services.impl;

import br.com.gustavodiniz.themovies.clients.MovieClient;
import br.com.gustavodiniz.themovies.clients.WeatherClient;
import br.com.gustavodiniz.themovies.dtos.TheMovieDbApiResponse;
import br.com.gustavodiniz.themovies.dtos.WeatherTheMapApiResponse;
import br.com.gustavodiniz.themovies.enums.GenresEnum;
import br.com.gustavodiniz.themovies.models.MovieModel;
import br.com.gustavodiniz.themovies.repositories.MovieRepository;
import br.com.gustavodiniz.themovies.services.MovieService;
import br.com.gustavodiniz.themovies.services.exceptions.ErrorException;
import br.com.gustavodiniz.themovies.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieClient movieClient;

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${services.weather-api.appId}")
    private String appId;

    @Value("${services.weather-api.units}")
    private String units;

    @Value("${services.weather-api.lang}")
    private String language;

    @Override
    public TheMovieDbApiResponse findTopRated(String apiKey, String language, Long page) {
        var response = movieClient.findTopRated(apiKey, language, page).orElseThrow(() -> new ErrorException("Unable to complete the request"));
        List<MovieModel> movies = response.getResults().stream().map(x -> modelMapper.map(x, MovieModel.class)).collect(Collectors.toList());
        movieRepository.saveAll(movies);
        return response;
    }

    @Override
    public Page<MovieModel> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public MovieModel findById(Long id) {
        Optional<MovieModel> movieModel = movieRepository.findById(id);
        return movieModel.orElseThrow(() -> new ObjectNotFoundException("Movie not found."));
    }

    @Override
    public Page<MovieModel> findSuggestionsByWeather(String city, Pageable pageable) {
        var weather = weatherClient.getWeatherByCity(appId, units, language, city)
                .orElseThrow(() -> new ErrorException("Unable to complete the request"));

        return findMoviesByGenres(weather);
    }

    private PageImpl<MovieModel> findMoviesByGenres(WeatherTheMapApiResponse weather) {

        if (weather.getMain().getTemp() > 40L) {
            List<MovieModel> listMovies = movieRepository.findByGenres(GenresEnum.ACTION.getId());
            return new PageImpl<>(listMovies);
        }

        if (weather.getMain().getTemp() >= 36L && weather.getMain().getTemp() <= 40L) {
            List<MovieModel> listMovies = movieRepository.findByGenres(GenresEnum.COMEDY.getId());
            return new PageImpl<>(listMovies);
        }

        if (weather.getMain().getTemp() > 20L && weather.getMain().getTemp() <= 35L) {
            List<MovieModel> listMovies = movieRepository.findByGenres(GenresEnum.ANIMATION.getId());
            return new PageImpl<>(listMovies);
        }

        if (weather.getMain().getTemp() >= 0L && weather.getMain().getTemp() <= 20L) {
            List<MovieModel> listMovies = movieRepository.findByGenres(GenresEnum.DRAMA.getId());
            return new PageImpl<>(listMovies);
        }

        if (weather.getMain().getTemp() < 0L) {
            List<MovieModel> listMovies = movieRepository.findByGenres(GenresEnum.DOCUMENTARY.getId());
            return new PageImpl<>(listMovies);
        }

        return new PageImpl<>(new ArrayList<>());
    }
}
