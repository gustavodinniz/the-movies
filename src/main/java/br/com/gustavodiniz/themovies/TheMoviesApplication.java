package br.com.gustavodiniz.themovies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TheMoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheMoviesApplication.class, args);
	}

}
