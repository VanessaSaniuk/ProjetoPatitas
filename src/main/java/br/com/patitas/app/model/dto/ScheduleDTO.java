package br.com.patitas.app.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ScheduleDTO(

        @NotNull @Min(1)
        Long id
) {
}
