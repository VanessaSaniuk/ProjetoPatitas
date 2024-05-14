package br.com.patitas.app.controller;

import br.com.patitas.app.enums.Specialization;
import br.com.patitas.app.model.Appointment;
import br.com.patitas.app.model.dto.AppointmentCreationDTO;
import br.com.patitas.app.model.dto.AppointmentReportDTO;
import br.com.patitas.app.model.dto.AppointmentUpdateDTO;
import br.com.patitas.app.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    @Autowired
    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<Appointment> postAppointment(
            @Valid @RequestBody AppointmentCreationDTO appointmentCreationDTO
    ) {
        Appointment appointment = service.createAppointment(appointmentCreationDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appointment.getId())
                .toUri();

        return ResponseEntity.created(uri).body(appointment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(
            @PathVariable Long id
    ) {
        Appointment appointment = service.findById(id);
        return ResponseEntity.ok().body(appointment);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllById() {
        List<Appointment> appointments = service.findAll();
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping("/report")
    public ResponseEntity<AppointmentReportDTO> appointmentsReport() {
        List<Appointment> activeAppointments = service.findAllActive();
        List<Appointment> endedAppointments = service.findAllEnded();
        List<Appointment> cancelledAppointments = service.findAllCancelled();
        Long active = (long) activeAppointments.size();
        Long ended = (long) endedAppointments.size();
        Long cancelled = (long) cancelledAppointments.size();
        Map<Specialization, Long> countSpecialization = service.countSpecialization();
        AppointmentReportDTO reportDTO = new AppointmentReportDTO(active, ended, cancelled, countSpecialization);
        return ResponseEntity.ok().body(reportDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Appointment> updateById(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentUpdateDTO appointmentUpdateDTO) {
        Appointment appointment = service.updateById(id, appointmentUpdateDTO);
        return ResponseEntity.ok().body(appointment);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancelById(
            @PathVariable Long id) {
        Appointment appointment = service.cancelAppointmentById(id);
        return ResponseEntity.ok().body(appointment);
    }

    @PatchMapping("/{id}/end")
    public ResponseEntity<Appointment> endById(
            @PathVariable Long id) {
        Appointment appointment = service.endAppointmentById(id);
        return ResponseEntity.ok().body(appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
