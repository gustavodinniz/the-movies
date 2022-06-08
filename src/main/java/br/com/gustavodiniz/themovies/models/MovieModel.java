package br.com.gustavodiniz.themovies.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_MOVIES")
public class MovieModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID movieId;

//    public List<Integer> genreIds;

    public Long id;

    public String originalLanguage;

    @Column(nullable = false)
    public String original_title;

    public String overview;

    public Double popularity;

    public String releaseDate;

    @Column(nullable = false)
    public String title;

    public Double voteAverage;

    public Long voteCount;
}
