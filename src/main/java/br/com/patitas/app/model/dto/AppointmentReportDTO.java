package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.Specialization;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record AppointmentReportDTO(

        @JsonProperty("active_appointments")
        Long active,

        @JsonProperty("ended_appointments")
        Long ended,

        @JsonProperty("cancelled_appointments")
        Long cancelled,

        @JsonProperty("appointments_specialization_count")
        Map<Specialization, Long> countSpecialization
) {
}
