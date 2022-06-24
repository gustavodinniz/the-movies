package br.com.gustavodiniz.themovies.services.impl;

import br.com.gustavodiniz.themovies.clients.MovieClient;
import br.com.gustavodiniz.themovies.clients.WeatherClient;
import br.com.gustavodiniz.themovies.dtos.MovieDTO;
import br.com.gustavodiniz.themovies.dtos.TheMovieDbApiResponse;
import br.com.gustavodiniz.themovies.dtos.WeatherTheMapApiResponse;
import br.com.gustavodiniz.themovies.enums.GenresEnum;
import br.com.gustavodiniz.themovies.models.MovieModel;
import br.com.gustavodiniz.themovies.repositories.MovieRepository;
import br.com.gustavodiniz.themovies.services.MovieService;
import br.com.gustavodiniz.themovies.services.exceptions.DataIntegrityException;
import br.com.gustavodiniz.themovies.services.exceptions.ErrorException;
import br.com.gustavodiniz.themovies.services.exceptions.ObjectNotFoundException;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
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
    public Page<MovieModel> getSuggestionsByWeather(String city, Pageable pageable) {
        var weather = weatherClient.getWeatherByCity(appId, units, language, city)
                .orElseThrow(() -> new ErrorException("Unable to complete the request"));
        log.info("Current temperature in {} is {} degrees Celsius.", city, weather.getMain().getTemp());
        return findMoviesByGenres(weather);
    }

    @Override
    public MovieModel create(MovieDTO movieDTO) {
        findByTitle(movieDTO);
        return movieRepository.save(modelMapper.map(movieDTO, MovieModel.class));
    }

    @Override
    public void delete(Long id) {
        findById(id);
        movieRepository.deleteById(id);
    }

    @Override
    public MovieModel update(MovieDTO movieDTO) {
        return movieRepository.save(modelMapper.map(movieDTO, MovieModel.class));
    }

    private PageImpl<MovieModel> findMoviesByGenres(WeatherTheMapApiResponse weather) {

        if (weather.getMain().getTemp() > 40L) {
            List<MovieModel> listMovies = movieRepository.findByGenres(GenresEnum.ACTION.getId());
            log.info("Suggestion: {} movies.", GenresEnum.ACTION.getName());
            return new PageImpl<>(listMovies);
        }

        if (weather.getMain().getTemp() >= 36L && weather.getMain().getTemp() <= 40L) {
            List<MovieModel> listMovies = movieRepository.findByGenres(GenresEnum.COMEDY.getId());
            log.info("Suggestion: {} movies.", GenresEnum.COMEDY.getName());
            return new PageImpl<>(listMovies);
        }

        if (weather.getMain().getTemp() > 20L && weather.getMain().getTemp() <= 35L) {
            List<MovieModel> listMovies = movieRepository.findByGenres(GenresEnum.ANIMATION.getId());
            log.info("Suggestion: {} movies.", GenresEnum.ANIMATION.getName());
            return new PageImpl<>(listMovies);
        }

        if (weather.getMain().getTemp() >= 0L && weather.getMain().getTemp() <= 20L) {
            List<MovieModel> listMovies = movieRepository.findByGenres(GenresEnum.DRAMA.getId());
            log.info("Suggestion: {} movies.", GenresEnum.DRAMA.getName());
            return new PageImpl<>(listMovies);
        }

        if (weather.getMain().getTemp() < 0L) {
            List<MovieModel> listMovies = movieRepository.findByGenres(GenresEnum.DOCUMENTARY.getId());
            log.info("Suggestion: {}.", GenresEnum.DOCUMENTARY.getName());
            return new PageImpl<>(listMovies);
        }

        return new PageImpl<>(new ArrayList<>());
    }

    private void findByTitle(MovieDTO movieDTO) {
        Optional<MovieModel> movieModelOptional = movieRepository.findByTitle(movieDTO.getTitle());
        if (movieModelOptional.isPresent() && !movieModelOptional.get().getId().equals(movieDTO.getId())) {
            throw new DataIntegrityException("There is already a movie with that name registered in the system.");
        }
    }
}
