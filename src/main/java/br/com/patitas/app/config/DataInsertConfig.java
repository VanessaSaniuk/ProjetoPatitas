package br.com.patitas.app.config;

import br.com.patitas.app.enums.Species;
import br.com.patitas.app.model.Owner;
import br.com.patitas.app.model.Pet;
import br.com.patitas.app.repository.AppointmentRepository;
import br.com.patitas.app.repository.OwnerRepository;
import br.com.patitas.app.repository.PetRepository;
import br.com.patitas.app.repository.VetRepository;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class DataInsertConfig implements CommandLineRunner {

    private final AppointmentRepository appointmentRepository;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;

    @Override
    public void run(String... args) throws Exception {
        Owner owner = Owner.builder().name("Vinicius").document("44871365808").build();
        ownerRepository.save(owner);
        Pet pet = Pet.builder().owner(owner).age(2).species(Species.CAT).race("Vagabundo").name("Simba").build();
        petRepository.save(pet);
        owner = ownerRepository.findById(1L).get();
        owner.getPets().add(pet);
        ownerRepository.save(owner);
    }
}
