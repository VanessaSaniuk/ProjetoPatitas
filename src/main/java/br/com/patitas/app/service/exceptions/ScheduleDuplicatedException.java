package br.com.patitas.app.service.exceptions;

public class ScheduleDuplicatedException extends RuntimeException {

    public ScheduleDuplicatedException(String message) {
        super(message);
    }
}
