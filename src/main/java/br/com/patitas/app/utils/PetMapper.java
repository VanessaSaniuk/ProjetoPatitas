package br.com.patitas.app.utils;

import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.dto.PetCreationDTO;
import br.com.patitas.app.model.dto.PetResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetMapper {

    PetResponseDTO petToResponseDTO(Pet source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    Pet creationDTOtoPet(PetCreationDTO source);
}
