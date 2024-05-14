package br.com.patitas.app.controller;

import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.dto.ScheduleCreationDTO;
import br.com.patitas.app.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService service;


    @Autowired
    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Schedule> postSchedule(
            @Valid @RequestBody ScheduleCreationDTO scheduleCreationDTO
    ) {
        Schedule schedule = service.createSchedule(scheduleCreationDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(schedule.getId())
                .toUri();

        return ResponseEntity.created(uri).body(schedule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getById(
            @PathVariable Long id
    ) {
        Schedule schedule = service.findById(id);
        return ResponseEntity.ok().body(schedule);
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllById() {
        List<Schedule> schedules = service.findAll();
        return ResponseEntity.ok().body(schedules);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
