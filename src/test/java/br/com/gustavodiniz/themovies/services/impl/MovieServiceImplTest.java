package br.com.gustavodiniz.themovies.services.impl;

import br.com.gustavodiniz.themovies.dtos.MovieDTO;
import br.com.gustavodiniz.themovies.models.MovieModel;
import br.com.gustavodiniz.themovies.repositories.MovieRepository;
import br.com.gustavodiniz.themovies.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
class MovieServiceImplTest {

    private final Long id = 999L;

    private final Set<Long> genres = new HashSet<>();
    private final String overview = "Good movie.";
    private final Double popularity = 99.999;
    private final String releaseDate = "2022-01-01";
    private final String title = "The Lincoln Lawyer";
    private final Double voteAverage = 10.0;
    private final Long voteCount = 10000L;

    @InjectMocks
    private MovieServiceImpl service;

    @Mock
    private MovieRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private MovieModel movieModel;

    private MovieDTO movieDTO;

    private Optional<MovieModel> movieModelOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startMovie();
    }

    @Test
    void whenFindByIdThenReturnAnMovie() {
        when(repository.findById(anyLong())).thenReturn(movieModelOptional);

        MovieModel response = service.findById(id);

        assertNotNull(response);
        assertEquals(MovieModel.class, response.getClass());
        assertEquals(id, response.getId());
        assertEquals(overview, response.getOverview());
        assertEquals(popularity, response.getPopularity());
        assertEquals(releaseDate, response.getReleaseDate());
        assertEquals(title, response.getTitle());
        assertEquals(voteAverage, response.getVoteAverage());
        assertEquals(voteCount, response.getVoteCount());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Movie not found."));

        try {
            service.findById(id);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Movie not found.", exception.getMessage());
        }
    }


    @Test
    void whenCreateTheReturnSuccess() {
        when(repository.save(any())).thenReturn(movieModel);

        MovieModel response = service.create(movieDTO);

        assertNotNull(response);
        assertEquals(MovieModel.class, response.getClass());
        assertEquals(id, response.getId());
        assertEquals(overview, response.getOverview());
        assertEquals(popularity, response.getPopularity());
        assertEquals(releaseDate, response.getReleaseDate());
        assertEquals(title, response.getTitle());
        assertEquals(voteAverage, response.getVoteAverage());
        assertEquals(voteCount, response.getVoteCount());
    }

    @Test
    void whenUpdateTheReturnSuccess() {
        when(repository.save(any())).thenReturn(movieModel);

        MovieModel response = service.update(movieDTO);

        assertNotNull(response);
        assertEquals(MovieModel.class, response.getClass());
        assertEquals(id, response.getId());
        assertEquals(overview, response.getOverview());
        assertEquals(popularity, response.getPopularity());
        assertEquals(releaseDate, response.getReleaseDate());
        assertEquals(title, response.getTitle());
        assertEquals(voteAverage, response.getVoteAverage());
        assertEquals(voteCount, response.getVoteCount());
    }

    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyLong())).thenReturn(movieModelOptional);
        doNothing().when(repository).deleteById(anyLong());
        service.delete(id);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteWithObjectNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException("Movie not found."));
        try {
            service.delete(id);
        } catch (Exception exception) {
            assertEquals(ObjectNotFoundException.class, exception.getClass());
            assertEquals("Movie not found.", exception.getMessage());
        }
    }

    private void startMovie() {
        movieModel = new MovieModel(id, genres, overview, popularity, releaseDate, title, voteAverage, voteCount);
        movieDTO = new MovieDTO(id, overview, popularity, releaseDate, title, voteAverage, voteCount);
        movieModelOptional = Optional.of(new MovieModel(id, genres, overview, popularity, releaseDate, title, voteAverage, voteCount));
    }
}