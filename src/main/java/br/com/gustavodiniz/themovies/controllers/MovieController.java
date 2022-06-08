package br.com.gustavodiniz.themovies.controllers;

import br.com.gustavodiniz.themovies.dtos.MovieDTO;
import br.com.gustavodiniz.themovies.models.MovieModel;
import br.com.gustavodiniz.themovies.services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<MovieDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "movieId", direction = Sort.Direction.ASC) Pageable pageable) {
        var movieDTO = movieService.findAll(pageable)
                .stream()
                .map(x -> modelMapper.map(x, MovieDTO.class)).collect(Collectors.toList());
        Page<MovieDTO> page = new PageImpl<>(movieDTO);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
        MovieModel movieModel = movieService.findById(id);
        return ResponseEntity.ok().body(modelMapper.map(movieModel, MovieDTO.class));
    }

    @GetMapping(value = "/suggestions/weather")
    public ResponseEntity<Page<MovieDTO>> findSuggestionsByWeather(@RequestParam(name = "city", required = true) String city,
                                                                   @PageableDefault(page = 0, size = 10, sort = "movieId", direction = Sort.Direction.ASC) Pageable pageable) {
        var movieDTO = movieService.findSuggestionsByWeather(city, pageable)
                .stream()
                .map(x -> modelMapper.map(x, MovieDTO.class)).collect(Collectors.toList());
        Page<MovieDTO> page = new PageImpl<>(movieDTO);
        return ResponseEntity.ok().body(page);
    }
}
