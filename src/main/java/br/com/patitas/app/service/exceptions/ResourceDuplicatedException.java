package br.com.patitas.app.service.exceptions;

public class ResourceDuplicatedException extends RuntimeException {

    public ResourceDuplicatedException(String message) {
        super(message);
    }
}
