package br.com.gustavodiniz.themovies.services;

import br.com.gustavodiniz.themovies.dtos.WeatherTheMapApiResponse;

public interface WeatherService {
    WeatherTheMapApiResponse getWeatherByCity(String appId, String units, String language, String city);
}
