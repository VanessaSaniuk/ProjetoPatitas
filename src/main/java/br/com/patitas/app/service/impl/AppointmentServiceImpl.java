package br.com.patitas.app.service.impl;

import br.com.patitas.app.enums.AppointmentStatus;
import br.com.patitas.app.enums.Specialization;
import br.com.patitas.app.model.Appointment;
import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.AppointmentCreationDTO;
import br.com.patitas.app.model.dto.AppointmentUpdateDTO;
import br.com.patitas.app.repository.AppointmentRepository;
import br.com.patitas.app.repository.PetRepository;
import br.com.patitas.app.repository.ScheduleRepository;
import br.com.patitas.app.repository.VetRepository;
import br.com.patitas.app.service.AppointmentService;
import br.com.patitas.app.service.exceptions.AppointmentStatusException;
import br.com.patitas.app.service.exceptions.ResourceNotFoundException;
import br.com.patitas.app.service.exceptions.ScheduleAlreadyUsedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    private final PetRepository petRepository;

    private final VetRepository vetRepository;

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repository, PetRepository petRepository, VetRepository vetRepository, ScheduleRepository scheduleRepository) {
        this.repository = repository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.scheduleRepository = scheduleRepository;
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
                .findById(appointment.getVet().getId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Vet not found by ID: " + appointment.getVet().getId())
                );

        Schedule schedule = appointment.getSchedule();

        Schedule newSchedule = findValidSchedule(vet, appointmentUpdateDTO.schedule());

        schedule.setAppointment(null);

        appointment.setSchedule(null);

        scheduleRepository.save(schedule);

        appointment.setSchedule(newSchedule);

        newSchedule.setAppointment(appointment);

        return repository.save(appointment);
    }

    @Override
    public Appointment cancelAppointmentById(Long id) {

        Appointment appointment = findById(id);

        if (appointment.getStatus() != AppointmentStatus.ACTIVE) {
            throw new AppointmentStatusException("Can't modify appointment status if isn't active");
        }

        Schedule schedule = appointment.getSchedule();

        appointment.setSchedule(null);

        schedule.setAppointment(null);

        appointment.setStatus(AppointmentStatus.CANCELLED);

        return repository.save(appointment);
    }

    @Override
    public Appointment endAppointmentById(Long id) {

        Appointment appointment = findById(id);

        if (appointment.getStatus() != AppointmentStatus.ACTIVE) {
            throw new AppointmentStatusException("Can't modify appointment status if isn't active");
        }

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

    @Override
    public Map<Specialization, Long> countSpecialization() {

        List<Appointment> appointments = findAll();

        List<Vet> vets = appointments
                .stream()
                .map(Appointment::getVet)
                .toList();

        Map<Specialization, Long> specializationsCount = vets
                .stream()
                .collect(
                        Collectors.groupingBy(Vet::getSpecialization, Collectors.counting())
                );

        return specializationsCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private Schedule findValidSchedule(Vet vet, Instant date) {

        Set<Schedule> schedules = vet.getSchedules();

        Schedule schedule = schedules.stream()
                .filter(x -> x.getStartTime().equals(date)).findFirst()
                .orElseThrow(
                        () -> new ResourceNotFoundException("Schedule not found with specified date: " + date)
                );

        if (schedule.getAppointment() != null) {
            throw new ScheduleAlreadyUsedException("Schedule specified is already being used");
        }

        return schedule;
    }
}


