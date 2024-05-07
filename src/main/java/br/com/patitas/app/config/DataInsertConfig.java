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

    }
}
