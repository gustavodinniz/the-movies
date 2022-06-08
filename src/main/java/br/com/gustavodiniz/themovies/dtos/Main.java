package br.com.gustavodiniz.themovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Main {

    public Double temp;

    @JsonProperty("feels_like")
    public Double feelsLike;

    @JsonProperty("temp_min")
    public Double tempMin;

    @JsonProperty("temp_max")
    public Integer tempMax;

    public Integer pressure;

    public Integer humidity;
}
