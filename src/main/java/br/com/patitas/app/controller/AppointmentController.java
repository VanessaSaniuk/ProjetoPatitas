package br.com.patitas.app.controller;

import br.com.patitas.app.model.Appointment;
import br.com.patitas.app.model.dto.AppointmentCreationDTO;
import br.com.patitas.app.model.dto.AppointmentResponseDTO;
import br.com.patitas.app.model.dto.AppointmentUpdateDTO;
import br.com.patitas.app.service.AppointmentService;
import br.com.patitas.app.utils.AppointmentMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    private final AppointmentMapper mapper;

    @Autowired
    public AppointmentController(AppointmentService service, AppointmentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping()
    public ResponseEntity<AppointmentResponseDTO> postAppointment(
            @Valid @RequestBody AppointmentCreationDTO appointmentCreationDTO
    ) {
        Appointment appointment = service.createAppointment(appointmentCreationDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appointment.getId())
                .toUri();

        return ResponseEntity.created(uri).body(mapper.appointmentToResponseDTO(appointment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getById(
            @PathVariable Long id
    ) {
        Appointment appointment = service.findById(id);
        return ResponseEntity.ok().body(mapper.appointmentToResponseDTO(appointment));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllById() {
        List<Appointment> appointments = service.findAll();
        return ResponseEntity.ok().body(appointments.stream().map(mapper::appointmentToResponseDTO).toList());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateById(
            @PathVariable Long id,
            @Valid AppointmentUpdateDTO appointmentUpdateDTO) {
        Appointment appointment = service.updateById(id, appointmentUpdateDTO);
        return ResponseEntity.ok().body(mapper.appointmentToResponseDTO(appointment));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponseDTO> cancelById(
            @PathVariable Long id) {
        Appointment appointment = service.cancelAppointmentById(id);
        return ResponseEntity.ok().body(mapper.appointmentToResponseDTO(appointment));
    }

    @PatchMapping("/{id}/end")
    public ResponseEntity<AppointmentResponseDTO> endById(
            @PathVariable Long id) {
        Appointment appointment = service.endAppointmentById(id);
        return ResponseEntity.ok().body(mapper.appointmentToResponseDTO(appointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
