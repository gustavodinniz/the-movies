package br.com.gustavodiniz.themovies.repositories;

import br.com.gustavodiniz.themovies.models.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<MovieModel, UUID> {
}
