package br.com.patitas.app.utils;

import br.com.patitas.app.model.Appointment;
import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.AppointmentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "vet", target = "vetId", qualifiedByName = "vetToVetId")
    @Mapping(source = "pet", target = "petId", qualifiedByName = "petToPetId")
    @Mapping(source = "schedule", target = "scheduleId", qualifiedByName = "scheduleToScheduleId")
    AppointmentResponseDTO appointmentToResponseDTO(Appointment source);

    @Named("vetToVetId")
    default Long vetToVetId(Vet vet) {
        if (vet == null) {
            return null;
        }
        return vet.getId();
    }

    @Named("petToPetId")
    default Long petToPetId(Pet pet) {
        if (pet == null) {
            return null;
        }
        return pet.getId();
    }

    @Named("scheduleToScheduleId")
    default Long scheduleToScheduleId(Schedule schedule) {
        if (schedule == null) {
            return null;
        }
        return schedule.getId();
    }
}
