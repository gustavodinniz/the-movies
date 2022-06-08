package br.com.gustavodiniz.themovies.clients;

import br.com.gustavodiniz.themovies.dtos.WeatherTheMapApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(
        url = "${services.weather-api.url}",
        name = "weatherClient",
        contextId = "weatherClientId"
)
public interface WeatherClient {

    @GetMapping
    Optional<WeatherTheMapApiResponse> getWeatherByCity(
            @RequestParam(name = "appid", required = true) String appId,
            @RequestParam(name = "units", required = false) String units,
            @RequestParam(name = "lang", required = false) String language,
            @RequestParam(name = "q", required = true) String city
    );
}
