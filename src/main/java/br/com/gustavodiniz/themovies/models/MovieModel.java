package br.com.gustavodiniz.themovies.models;

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
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "id"))
    private Set<Long> genres;

    @Column(columnDefinition = "text")
    private String overview;

    private Double popularity;

    private String releaseDate;

    private String title;

    private Double voteAverage;

    private Long voteCount;
}
