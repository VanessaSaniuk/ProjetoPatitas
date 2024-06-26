package br.com.patitas.app.controller;

import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.dto.PetCreationDTO;
import br.com.patitas.app.model.dto.PetUpdateDTO;
import br.com.patitas.app.service.PetService;
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

    @Autowired
    public PetController(PetService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Pet> postPet(
            @Valid @RequestBody PetCreationDTO petCreationDTO
    ) {
        Pet pet = service.createPet(petCreationDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pet.getId())
                .toUri();

        return ResponseEntity.created(uri).body(pet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getById(
            @PathVariable Long id
    ) {
        Pet pet = service.findById(id);
        return ResponseEntity.ok().body(pet);
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllById() {
        List<Pet> pets = service.findAll();
        return ResponseEntity.ok().body(pets);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pet> updateById(
            @PathVariable Long id,
            @Valid @RequestBody PetUpdateDTO petUpdateDTO
    ) {
        Pet pet = service.updatePetById(id, petUpdateDTO);
        return ResponseEntity.ok().body(pet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
