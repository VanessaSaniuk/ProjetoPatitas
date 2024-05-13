package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.Instant;

public record ScheduleCreationDTO(

        @NotNull
        @JsonProperty("day_of_week")
        DayOfWeek dayOfWeek,

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
