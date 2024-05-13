package br.com.patitas.app.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record AppointmentCreationDTO(

        @NotNull
        @Min(1)
        @JsonProperty("pet_id")
        Long petId,

        @NotNull
        @Min(1)
        @JsonProperty("vet_id")
        Long vetId,

        @FutureOrPresent
        @NotNull
        Instant schedule
) {
}
