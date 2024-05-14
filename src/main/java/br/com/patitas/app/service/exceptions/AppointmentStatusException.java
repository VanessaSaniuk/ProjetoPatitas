package br.com.patitas.app.service.exceptions;

public class AppointmentStatusException extends RuntimeException {

    public AppointmentStatusException(String message) {
        super(message);
    }
}
