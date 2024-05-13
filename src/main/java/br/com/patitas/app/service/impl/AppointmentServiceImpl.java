package br.com.patitas.app.service.impl;

import br.com.patitas.app.enums.AppointmentStatus;
import br.com.patitas.app.model.Appointment;
import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.AppointmentCreationDTO;
import br.com.patitas.app.model.dto.AppointmentUpdateDTO;
import br.com.patitas.app.repository.AppointmentRepository;
import br.com.patitas.app.repository.PetRepository;
import br.com.patitas.app.repository.VetRepository;
import br.com.patitas.app.service.AppointmentService;
import br.com.patitas.app.service.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    private final PetRepository petRepository;

    private final VetRepository vetRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repository, PetRepository petRepository, VetRepository vetRepository) {
        this.repository = repository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
    }

    @Override
    public Appointment createAppointment(AppointmentCreationDTO appointmentCreationDTO) {
        Appointment appointment = new Appointment();

        Pet pet = petRepository
                .findById(appointmentCreationDTO.petId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Pet not found by ID: " + appointmentCreationDTO.petId())
                );

        appointment.setPet(pet);

        pet.getAppointments().add(appointment);

        Vet vet = vetRepository
                .findById(appointmentCreationDTO.vetId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Vet not found by ID: " + appointmentCreationDTO.vetId())
                );

        appointment.setVet(vet);

        vet.getAppointments().add(appointment);

        Schedule schedule = findValidSchedule(vet, appointmentCreationDTO.schedule());

        appointment.setSchedule(schedule);

        schedule.setAppointment(appointment);

        appointment.setStatus(AppointmentStatus.ACTIVE);

        return repository.save(appointment);
    }

    @Override
    public Appointment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Appointment not found by ID: " + id)
                );
    }

    @Override
    public List<Appointment> findAll() {
        return repository.findAll();
    }

    @Override
    public Appointment updateById(Long id, AppointmentUpdateDTO appointmentUpdateDTO) {
        Appointment appointment = findById(id);

        Vet vet = vetRepository
                .findById(appointmentUpdateDTO.vetId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Vet not found by ID: " + appointmentUpdateDTO.vetId())
                );

        appointment.setVet(vet);

        Schedule schedule = findValidSchedule(vet, appointmentUpdateDTO.schedule());

        appointment.setSchedule(schedule);

        return repository.save(appointment);
    }

    @Override
    public Appointment cancelAppointmentById(Long id) {
        Appointment appointment = findById(id);
        Schedule schedule = appointment.getSchedule();
        appointment.setSchedule(null);
        schedule.setAppointment(null);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return repository.save(appointment);
    }

    @Override
    public Appointment endAppointmentById(Long id) {
        Appointment appointment = findById(id);
        Schedule schedule = appointment.getSchedule();
        appointment.setSchedule(null);
        schedule.setAppointment(null);
        appointment.setStatus(AppointmentStatus.ENDED);
        return repository.save(appointment);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(findById(id).getId());
    }

    @Override
    public List<Appointment> findAllActive() {
        return repository.findAll()
                .stream()
                .filter(x -> x.getStatus() == AppointmentStatus.ACTIVE)
                .toList();
    }

    @Override
    public List<Appointment> findAllCancelled() {
        return repository.findAll()
                .stream()
                .filter(x -> x.getStatus() == AppointmentStatus.CANCELLED)
                .toList();
    }

    @Override
    public List<Appointment> findAllEnded() {
        return repository.findAll()
                .stream()
                .filter(x -> x.getStatus() == AppointmentStatus.ENDED)
                .toList();
    }

    private Schedule findValidSchedule(Vet vet, Instant date) {
        Set<Schedule> schedules = vet.getSchedules();
        Schedule schedule = null;
        schedule = schedules.stream()
                .filter(x -> x.getStartTime().equals(date)).findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException("Schedule not found with specified date: " + date)
                );
        return schedule;
    }
}


