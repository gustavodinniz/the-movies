package br.com.gustavodiniz.themovies.repositories;

import br.com.gustavodiniz.themovies.models.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieModel, Long> {
}
