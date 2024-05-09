package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.Species;
import jakarta.validation.constraints.*;

public record PetCreationDTO(

        @NotBlank @Size(min = 1, max = 24)
        String name,

        @NotNull
        Species species,

        @NotBlank @Size(max = 24)
        String race,

        @NotNull @Min(value = 0, message = "Min value for age is 0") @Max(value = 24, message = "Max value for age is 24")
        Integer age
) {
}
