package br.com.patitas.app.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record ScheduleCreationDTO(

        @FutureOrPresent
        @NotNull
        @JsonProperty("start_time")
        Instant startTime,

        @Future
        @NotNull
        @JsonProperty("end_time")
        Instant endTime,

        @NotNull @Min(1)
        @JsonProperty("vet_id")
        Long vetId
) {
}
