package br.com.gustavodiniz.themovies.controllers;

import br.com.gustavodiniz.themovies.dtos.MovieDTO;
import br.com.gustavodiniz.themovies.models.MovieModel;
import br.com.gustavodiniz.themovies.services.MovieService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<MovieDTO>> findAll(@PageableDefault(page = 0, size = 25, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        var movieDTO = movieService.findAll(pageable)
                .stream()
                .map(x -> modelMapper.map(x, MovieDTO.class)).collect(Collectors.toList());
        Page<MovieDTO> page = new PageImpl<>(movieDTO);
        log.info("Showing all movies.");
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
        MovieModel movieModel = movieService.findById(id);
        log.info("Showing movie with id: {}.", id);
        return ResponseEntity.ok().body(modelMapper.map(movieModel, MovieDTO.class));
    }

    @GetMapping(value = "/suggestions")
    public ResponseEntity<Page<MovieDTO>> getSuggestions(@RequestParam(name = "city", required = true) String city,
                                                                   @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        var movieDTO = movieService.getSuggestionsByWeather(city, pageable)
                .stream()
                .map(x -> modelMapper.map(x, MovieDTO.class)).collect(Collectors.toList());
        Page<MovieDTO> page = new PageImpl<>(movieDTO);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> create(@RequestBody @Valid MovieDTO movieDTO) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(movieService.create(movieDTO).getId()).toUri();
        log.info("New movie created successfully.");
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieDTO> delete(@PathVariable Long id) {
        movieService.delete(id);
        log.info("Movie with id: {} has been deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody @Valid MovieDTO movieDTO) {
        movieDTO.setId(id);
        MovieModel movieModel = movieService.update(movieDTO);
        log.info("Movie with id: {} has been updated successfully.", movieModel.getId());
        return ResponseEntity.ok().body(modelMapper.map(movieModel, MovieDTO.class));
    }
}
