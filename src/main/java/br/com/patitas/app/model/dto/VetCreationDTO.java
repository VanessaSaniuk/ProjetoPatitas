package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.Specialization;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VetCreationDTO(

        @NotBlank @Size(min = 1, max = 24)
        String name,

        @NotNull
        Specialization specialization
) {
}
