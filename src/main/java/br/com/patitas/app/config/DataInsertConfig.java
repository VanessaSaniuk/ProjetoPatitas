package br.com.patitas.app.config;

import br.com.patitas.app.enums.DayOfWeek;
import br.com.patitas.app.enums.Specialization;
import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.repository.*;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@Data
public class DataInsertConfig implements CommandLineRunner {

    private final AppointmentRepository appointmentRepository;
    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public void run(String... args) throws Exception {
    }
}
