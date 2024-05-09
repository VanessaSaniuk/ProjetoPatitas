package br.com.patitas.app.service;

import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.dto.PetCreationDTO;
import br.com.patitas.app.model.dto.PetUpdateDTO;

import java.util.List;

public interface PetService {

    Pet createPet(PetCreationDTO petCreationDTO);

    Pet findById(Long id);

    List<Pet> findAll();

    Pet updatePetById(Long id, PetUpdateDTO petUpdateDTO);

    void deleteById(Long id);

}
