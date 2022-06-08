package br.com.gustavodiniz.themovies.repositories;

import br.com.gustavodiniz.themovies.models.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieModel, Long> {

    List<MovieModel> findByGenres(Long genres);
}
