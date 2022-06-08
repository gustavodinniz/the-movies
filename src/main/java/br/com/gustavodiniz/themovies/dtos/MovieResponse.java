package br.com.gustavodiniz.themovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {

    @JsonProperty("genre_ids")
    public List<Integer> genreIds;

    public Long id;

    @JsonProperty("original_language")
    public String originalLanguage;

    @JsonProperty("originalTitle")
    public String original_title;

    public String overview;

    public Double popularity;

    @JsonProperty("release_date")
    public String releaseDate;

    public String title;

    @JsonProperty("vote_average")
    public Double voteAverage;

    @JsonProperty("vote_count")
    public Long voteCount;
}
