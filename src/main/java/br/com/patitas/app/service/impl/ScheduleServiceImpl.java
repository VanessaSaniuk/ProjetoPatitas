package br.com.patitas.app.service.impl;

import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.ScheduleCreationDTO;
import br.com.patitas.app.repository.ScheduleRepository;
import br.com.patitas.app.repository.VetRepository;
import br.com.patitas.app.service.ScheduleService;
import br.com.patitas.app.service.VetService;
import br.com.patitas.app.service.exceptions.ResourceNotFoundException;
import br.com.patitas.app.service.exceptions.ScheduleDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository repository;

    private final VetRepository vetRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository repository, VetService vetService, VetRepository vetRepository) {
        this.repository = repository;
        this.vetRepository = vetRepository;
    }

    @Override
    public Schedule createSchedule(ScheduleCreationDTO scheduleCreationDTO) {

        Schedule schedule = new Schedule();

        schedule.setStartTime(scheduleCreationDTO.startTime());
        schedule.setEndTime(scheduleCreationDTO.endTime());

        Vet vet = vetRepository
                .findById(scheduleCreationDTO.vetId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Vet not found by ID: " + scheduleCreationDTO.vetId())
                );

        verifyScheduleDuplicate(schedule, vet);

        schedule.setVet(vet);

        vet.getSchedules().add(schedule);

        return repository.save(schedule);
    }

    @Override
    public Schedule findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vet not found by ID: " + id));
    }

    @Override
    public List<Schedule> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(findById(id).getId());
    }

    private void verifyScheduleDuplicate(Schedule schedule, Vet vet) {

        Set<Schedule> schedules = vet.getSchedules();

        Optional<Schedule> optional = schedules
                .stream()
                .filter(
                        x -> x.getStartTime().equals(schedule.getStartTime())
                                || x.getEndTime().equals(schedule.getEndTime())
                ).findAny();

        if (optional.isPresent()) {
            throw new ScheduleDuplicatedException("Schedule with specified time already present in the Data Base");
        }
    }
}
