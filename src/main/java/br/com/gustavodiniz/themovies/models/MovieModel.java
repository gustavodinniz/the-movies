package br.com.gustavodiniz.themovies.models;

import br.com.gustavodiniz.themovies.enums.Genres;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "TB_MOVIES")
public class MovieModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long movieId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movieId"))
    private Set<Long> genres;

    private Long id;

    private String originalLanguage;

    private String original_title;

    @Column(columnDefinition = "text")
    private String overview;

    private Double popularity;

    private String releaseDate;

    private String title;

    private Double voteAverage;

    private Long voteCount;
}
