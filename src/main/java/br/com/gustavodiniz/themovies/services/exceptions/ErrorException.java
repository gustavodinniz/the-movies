package br.com.gustavodiniz.themovies.services.exceptions;

public class ErrorException extends RuntimeException {

    public ErrorException(String message) {
        super(message);
    }
}
