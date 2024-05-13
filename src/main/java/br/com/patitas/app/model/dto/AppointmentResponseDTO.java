package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AppointmentResponseDTO(

        @JsonProperty("pet_id")
        Long petId,

        @JsonProperty("vet_id")
        Long vetId,

        @JsonProperty("schedule_id")
        Long scheduleId,

        @JsonProperty("status")
        AppointmentStatus status
) {
}
