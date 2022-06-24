package br.com.gustavodiniz.themovies.repositories;

import br.com.gustavodiniz.themovies.models.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieModel, Long> {

    List<MovieModel> findByGenres(Long genres);

    Optional<MovieModel> findByTitle(String title);
}
