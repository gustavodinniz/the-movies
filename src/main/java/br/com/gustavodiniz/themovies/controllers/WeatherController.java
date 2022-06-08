package br.com.gustavodiniz.themovies.controllers;


import br.com.gustavodiniz.themovies.clients.WeatherClient;
import br.com.gustavodiniz.themovies.dtos.WeatherTheMapApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weathers")
public class WeatherController {

    @Autowired
    private WeatherClient weatherClient;

    @Value("${services.weather-api.appId}")
    private String appId;

    @Value("${services.weather-api.units}")
    private String units;

    @Value("${services.weather-api.lang}")
    private String language;

    private final String city = "Belo Horizonte";

    @GetMapping
    public WeatherTheMapApiResponse getWeatherByCity() {
        return weatherClient.getWeatherByCity(appId, units, language, city).orElse(new WeatherTheMapApiResponse());
    }
}
