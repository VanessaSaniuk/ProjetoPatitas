package br.com.patitas.app.service.impl;

import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.VetCreationDTO;
import br.com.patitas.app.model.dto.VetUpdateDTO;
import br.com.patitas.app.repository.ScheduleRepository;
import br.com.patitas.app.repository.VetRepository;
import br.com.patitas.app.service.VetService;
import br.com.patitas.app.service.exceptions.ResourceNotFoundException;
import br.com.patitas.app.utils.VetMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class VetServiceImpl implements VetService {

    private final VetRepository repository;

    private final VetMapper mapper;

    @Autowired
    public VetServiceImpl(VetRepository repository, VetMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Vet createVet(VetCreationDTO vetCreationDTO) {
        Vet vet = mapper.creationDTOtoVet(vetCreationDTO);
        return repository.save(vet);
    }

    @Override
    public Vet findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vet not found by ID: " + id));
    }

    @Override
    public List<Vet> findAll() {
        return repository.findAll();
    }

    @Override
    public Vet updateVetById(Long id, VetUpdateDTO vetUpdateDTO) {
        Vet vet = findById(id);
        updateName(vet, vetUpdateDTO);
        updateSpecialization(vet, vetUpdateDTO);
        return repository.save(vet);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(findById(id).getId());
    }

    @Override
    public Vet saveVet(Vet vet) {
        return repository.save(vet);
    }

    private void updateName(Vet vet, VetUpdateDTO vetUpdateDTO) {
        if (vetUpdateDTO.name() != null) {
            vet.setName(vetUpdateDTO.name());
        }
    }

    private void updateSpecialization(Vet vet, VetUpdateDTO vetUpdateDTO) {
        if (vetUpdateDTO.specialization() != null) {
            vet.setSpecialization(vetUpdateDTO.specialization());
        }
    }
}
