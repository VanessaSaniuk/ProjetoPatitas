package br.com.patitas.app.model.dto;

import br.com.patitas.app.enums.Specialization;
import br.com.patitas.app.model.Appointment;
import br.com.patitas.app.model.Schedule;

import java.util.Set;

public record VetResponseDTO(

        String name,

        Specialization specialization,

        Set<Appointment> appointments,

        Set<Schedule> schedules
) {
}
