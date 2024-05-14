package br.com.patitas.app.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record AppointmentUpdateDTO(

        @FutureOrPresent
        @NotNull
        @JsonProperty("schedule")
        Instant schedule
) {
}
