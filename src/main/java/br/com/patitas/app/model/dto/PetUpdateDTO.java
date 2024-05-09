package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.Species;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record PetUpdateDTO(

        @Size(max = 24)
        String name,

        Species species,

        @Size(max = 24)
        String race,

        @Min(0) @Max(24)
        Integer age
) {
}
