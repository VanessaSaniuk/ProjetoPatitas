package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.DayOfWeek;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record ScheduleResponseDTO(

        @JsonProperty("day_of_week")
        DayOfWeek dayOfWeek,

        @JsonProperty("start_time")
        Instant startTime,

        @JsonProperty("end_time")
        Instant endTime,

        AppointmentResponseDTO appointment,

        @JsonProperty("vet_id")
        Long vetId
) {
}
