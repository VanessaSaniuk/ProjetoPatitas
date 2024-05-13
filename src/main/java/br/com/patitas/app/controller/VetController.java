package br.com.patitas.app.controller;

import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.VetCreationDTO;
import br.com.patitas.app.model.dto.VetResponseDTO;
import br.com.patitas.app.model.dto.VetUpdateDTO;
import br.com.patitas.app.service.VetService;
import br.com.patitas.app.utils.VetMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vets")
public class VetController {

    private final VetService service;

    private final VetMapper mapper;

    @Autowired
    public VetController(VetService service, VetMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping()
    public ResponseEntity<VetResponseDTO> postVet(
            @Valid @RequestBody VetCreationDTO vetCreationDTO
    ) {
        Vet vet = service.createVet(vetCreationDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vet.getId())
                .toUri();

        return ResponseEntity.created(uri).body(mapper.vetToResponseDTO(vet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VetResponseDTO> getById(
            @PathVariable Long id
    ) {
        Vet vet = service.findById(id);
        return ResponseEntity.ok().body(mapper.vetToResponseDTO(vet));
    }

    @GetMapping
    public ResponseEntity<List<VetResponseDTO>> getAllById() {
        List<Vet> vets = service.findAll();
        return ResponseEntity.ok().body(vets.stream().map(mapper::vetToResponseDTO).toList());
    }


    @PatchMapping("/{id}")
    public ResponseEntity<VetResponseDTO> updateById(
            @PathVariable Long id,
            @Valid @RequestBody VetUpdateDTO vetUpdateDTO
    ) {
        Vet vet = service.updateVetById(id, vetUpdateDTO);
        return ResponseEntity.ok().body(mapper.vetToResponseDTO(vet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
