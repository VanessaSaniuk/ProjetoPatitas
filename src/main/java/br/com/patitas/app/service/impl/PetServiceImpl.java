package br.com.patitas.app.service.impl;

import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.dto.PetCreationDTO;
import br.com.patitas.app.model.dto.PetUpdateDTO;
import br.com.patitas.app.repository.PetRepository;
import br.com.patitas.app.service.PetService;
import br.com.patitas.app.service.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    private final PetRepository repository;

    @Autowired
    public PetServiceImpl(PetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pet createPet(PetCreationDTO petCreationDTO) {

        Pet pet = new Pet();

        pet.setName(petCreationDTO.name());

        pet.setRace(petCreationDTO.race());

        pet.setSpecies(petCreationDTO.species());

        pet.setAge(petCreationDTO.age());

        return repository.save(pet);
    }

    @Override
    public Pet findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found by ID: " + id));
    }

    @Override
    public List<Pet> findAll() {
        return repository.findAll();
    }

    @Override
    public Pet updatePetById(Long id, PetUpdateDTO petUpdateDTO) {

        Pet pet = findById(id);

        updateName(pet, petUpdateDTO);

        updateSpecies(pet, petUpdateDTO);

        updateRace(pet, petUpdateDTO);

        updateAge(pet, petUpdateDTO);

        return repository.save(pet);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(findById(id).getId());
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
