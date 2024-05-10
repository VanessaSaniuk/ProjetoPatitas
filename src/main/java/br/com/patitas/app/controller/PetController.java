package br.com.patitas.app.controller;

import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.dto.PetCreationDTO;
import br.com.patitas.app.model.dto.PetResponseDTO;
import br.com.patitas.app.model.dto.PetUpdateDTO;
import br.com.patitas.app.service.PetService;
import br.com.patitas.app.utils.PetMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService service;

    private final PetMapper mapper;

    @Autowired
    public PetController(PetService service, PetMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PetResponseDTO> postPet(
            @Valid @RequestBody PetCreationDTO petCreationDTO
    ) {
        Pet pet = service.createPet(petCreationDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pet.getId())
                .toUri();

        return ResponseEntity.created(uri).body(mapper.petToResponseDTO(pet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> getById(
            @PathVariable Long id
    ) {
        Pet pet = service.findById(id);
        return ResponseEntity.ok().body(mapper.petToResponseDTO(pet));
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> getAllById() {
        List<Pet> pets = service.findAll();
        return ResponseEntity.ok().body(pets.stream().map(mapper::petToResponseDTO).toList());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PetResponseDTO> updateById(
            @PathVariable Long id,
            @Valid @RequestBody PetUpdateDTO petUpdateDTO
    ) {
        Pet pet = service.updatePetById(id, petUpdateDTO);
        return ResponseEntity.ok().body(mapper.petToResponseDTO(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
