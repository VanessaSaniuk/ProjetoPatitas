package br.com.patitas.app.service.impl;

import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.dto.PetCreationDTO;
import br.com.patitas.app.model.dto.PetUpdateDTO;
import br.com.patitas.app.repository.PetRepository;
import br.com.patitas.app.service.PetService;
import br.com.patitas.app.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(PetCreationDTO petCreationDTO) {
        Pet pet = new Pet();
        pet.setName(petCreationDTO.name());
        pet.setSpecies(petCreationDTO.species());
        pet.setRace(petCreationDTO.race());
        pet.setAge(petCreationDTO.age());
        return petRepository.save(pet);
    }

    @Override
    public Pet findById(Long id) {
        return petRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found by ID: " + id));
    }

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Pet updatePetById(Long id, PetUpdateDTO petUpdateDTO) {
        Pet pet = findById(id);
        updateName(pet, petUpdateDTO);
        updateSpecies(pet, petUpdateDTO);
        updateRace(pet, petUpdateDTO);
        updateAge(pet, petUpdateDTO);
        petRepository.save(pet);
        return pet;
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(findById(id).getId());
    }

    private void updateName(Pet pet, PetUpdateDTO petUpdateDTO) {
        if (petUpdateDTO.name() != null) {
            pet.setName(petUpdateDTO.name());
        }
    }

    private void updateSpecies(Pet pet, PetUpdateDTO petUpdateDTO) {
        if (petUpdateDTO.species() != null) {
            pet.setSpecies(petUpdateDTO.species());
        }
    }

    private void updateRace(Pet pet, PetUpdateDTO petUpdateDTO) {
        if (petUpdateDTO.race() != null) {
            pet.setRace(petUpdateDTO.race());
        }
    }

    private void updateAge(Pet pet, PetUpdateDTO petUpdateDTO) {
        if (petUpdateDTO.age() != null) {
            pet.setAge(petUpdateDTO.age());
        }
    }
}
