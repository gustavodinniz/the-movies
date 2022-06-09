package br.com.gustavodiniz.themovies.configs;

import br.com.gustavodiniz.themovies.clients.MovieClient;
import br.com.gustavodiniz.themovies.models.MovieModel;
import br.com.gustavodiniz.themovies.repositories.MovieRepository;
import br.com.gustavodiniz.themovies.services.exceptions.ErrorException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieClient movieClient;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${services.the-movie-db-api.apiKey}")
    private String apiKey;

    @Value("${services.the-movie-db-api.language}")
    private String language;

    @Bean
    public void startDB() {
        Long page1 = 1L;
        Long page2 = 2L;
        Long page3 = 3L;
        Long page4 = 4L;
        Long page5 = 5L;

        var response1 = movieClient.findTopRated(apiKey, language, page1).orElseThrow(() -> new ErrorException("Unable to complete the request"));
        List<MovieModel> movies1 = response1.getResults().stream().map(x -> modelMapper.map(x, MovieModel.class)).collect(Collectors.toList());
        movieRepository.saveAll(movies1);
        log.info("Top rated movies have been saved successfully: page 1");

        var response2 = movieClient.findTopRated(apiKey, language, page2).orElseThrow(() -> new ErrorException("Unable to complete the request"));
        List<MovieModel> movies2 = response2.getResults().stream().map(x -> modelMapper.map(x, MovieModel.class)).collect(Collectors.toList());
        movieRepository.saveAll(movies2);
        log.info("Top rated movies have been saved successfully: page 2");

        var response3 = movieClient.findTopRated(apiKey, language, page3).orElseThrow(() -> new ErrorException("Unable to complete the request"));
        List<MovieModel> movies3 = response3.getResults().stream().map(x -> modelMapper.map(x, MovieModel.class)).collect(Collectors.toList());
        movieRepository.saveAll(movies3);
        log.info("Top rated movies have been saved successfully: page 3");

        var response4 = movieClient.findTopRated(apiKey, language, page4).orElseThrow(() -> new ErrorException("Unable to complete the request"));
        List<MovieModel> movies4 = response4.getResults().stream().map(x -> modelMapper.map(x, MovieModel.class)).collect(Collectors.toList());
        movieRepository.saveAll(movies4);
        log.info("Top rated movies have been saved successfully: page 4");

        var response5 = movieClient.findTopRated(apiKey, language, page5).orElseThrow(() -> new ErrorException("Unable to complete the request"));
        List<MovieModel> movies5 = response5.getResults().stream().map(x -> modelMapper.map(x, MovieModel.class)).collect(Collectors.toList());
        movieRepository.saveAll(movies5);
        log.info("Top rated movies have been saved successfully: page 5");

    }
}
