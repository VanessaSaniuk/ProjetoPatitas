package br.com.patitas.app.utils;

import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.ScheduleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(source = "vet", target = "vetId", qualifiedByName = "vetToVetId")
    ScheduleResponseDTO scheduleToResponseDTO(Schedule source);

    @Named("vetToVetId")
    default Long vetToVetId(Vet vet) {
        if (vet == null) {
            return null;
        }
        return vet.getId();
    }

}
