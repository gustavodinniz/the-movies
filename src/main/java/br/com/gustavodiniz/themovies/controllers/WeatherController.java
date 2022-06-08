package br.com.gustavodiniz.themovies.controllers;


import br.com.gustavodiniz.themovies.clients.WeatherClient;
import br.com.gustavodiniz.themovies.dtos.WeatherTheMapApiResponse;
import br.com.gustavodiniz.themovies.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weathers")
public class WeatherController {

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private WeatherService weatherService;

    @Value("${services.weather-api.appId}")
    private String appId;

    @Value("${services.weather-api.units}")
    private String units;

    @Value("${services.weather-api.lang}")
    private String language;

    @GetMapping
    public ResponseEntity<WeatherTheMapApiResponse> getWeatherByCity(@RequestParam(name = "city", required = true) String city) {
        return ResponseEntity.ok().body(weatherService.getWeatherByCity(appId, units, language, city));
    }
}
