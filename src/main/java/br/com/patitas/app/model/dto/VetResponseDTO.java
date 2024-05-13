package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.Specialization;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

public record VetResponseDTO(

        String name,

        Specialization specialization,

        @JsonIgnoreProperties(value = {"appointment", "vetId"})
        Set<ScheduleResponseDTO> schedules
) {
}
