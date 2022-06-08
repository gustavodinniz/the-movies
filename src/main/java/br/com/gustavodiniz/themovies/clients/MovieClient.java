package br.com.gustavodiniz.themovies.clients;

import br.com.gustavodiniz.themovies.dtos.TheMovieDbApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(
        url = "${services.the-movie-db-api.url}",
        name = "movieClient",
        contextId = "movieClientId"
)
public interface MovieClient {

    @GetMapping()
    Optional<TheMovieDbApiResponse> findTopRated(
            @RequestParam(name = "api_key", required = true) String apiKey,
            @RequestParam(name = "language", required = false) String language,
            @RequestParam(name = "page", required = false) Long page);
}
