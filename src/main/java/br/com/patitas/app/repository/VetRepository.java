package br.com.patitas.app.repository;

import br.com.patitas.app.model.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Long> {
}
