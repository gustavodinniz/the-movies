package br.com.gustavodiniz.themovies.services.impl;

import br.com.gustavodiniz.themovies.clients.WeatherClient;
import br.com.gustavodiniz.themovies.dtos.WeatherTheMapApiResponse;
import br.com.gustavodiniz.themovies.services.WeatherService;
import br.com.gustavodiniz.themovies.services.exceptions.ErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherClient weatherClient;

    @Override
    public WeatherTheMapApiResponse getWeatherByCity(String appId, String units, String language, String city) {
        return weatherClient.getWeatherByCity(appId, units, language, city).orElseThrow(() -> new ErrorException("Unable to complete the request"));
    }
}
