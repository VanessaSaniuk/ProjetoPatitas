package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.Specialization;
import jakarta.validation.constraints.Size;

public record VetUpdateDTO(

        @Size(min = 1, max = 24)
        String name,

        Specialization specialization
) {
}
