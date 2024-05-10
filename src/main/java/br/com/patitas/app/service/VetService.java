package br.com.patitas.app.service;

import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.VetCreationDTO;
import br.com.patitas.app.model.dto.VetUpdateDTO;

import java.util.List;

public interface VetService {

    Vet createVet(VetCreationDTO vetCreationDTO);

    Vet findById(Long id);

    List<Vet> findAll();

    Vet updateVetById(Long id, VetUpdateDTO vetUpdateDTO);

    void deleteById(Long id);

}
