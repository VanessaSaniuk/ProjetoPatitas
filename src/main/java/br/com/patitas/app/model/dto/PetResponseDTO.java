package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.Species;
import br.com.patitas.app.model.Appointment;

import java.util.Set;

public record PetResponseDTO(

        Set<Appointment> appointments,

        String name,

        Species species,

        String race,

        Integer age
) {
}
