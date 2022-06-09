package br.com.gustavodiniz.themovies.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainResponse {

    public Long temp;

    @JsonProperty("feels_like")
    public Long feelsLike;

    @JsonProperty("temp_min")
    public Long tempMin;

    @JsonProperty("temp_max")
    public Long tempMax;

    public Long pressure;

    public Long humidity;
}
