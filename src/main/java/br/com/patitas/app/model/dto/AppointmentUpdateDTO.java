package br.com.patitas.app.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record AppointmentUpdateDTO(

        @JsonProperty("appointment_id")
        @Min(1)
        @NotNull
        Long appointmentId,

        @JsonProperty("vet_id")
        @Min(1)
        @NotNull
        Long vetId,

        @FutureOrPresent
        @NotNull
        Instant schedule
) {
}
