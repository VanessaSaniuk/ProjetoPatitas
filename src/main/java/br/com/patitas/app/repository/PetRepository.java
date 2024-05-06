package br.com.patitas.app.repository;

import br.com.patitas.app.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
