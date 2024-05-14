package br.com.patitas.app.service.exceptions;

public class ScheduleAlreadyUsedException extends RuntimeException {

    public ScheduleAlreadyUsedException(String message) {
        super(message);
    }
}
