package br.com.patitas.app.service;

import br.com.patitas.app.model.Appointment;
import br.com.patitas.app.model.dto.AppointmentCreationDTO;
import br.com.patitas.app.model.dto.AppointmentUpdateDTO;

import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(AppointmentCreationDTO appointmentCreationDTO);

    Appointment findById(Long id);

    List<Appointment> findAll();

    Appointment updateById(Long id, AppointmentUpdateDTO appointmentUpdateDTO);

    Appointment cancelAppointmentById(Long id);

    Appointment endAppointmentById(Long id);

    void deleteById(Long id);

    List<Appointment> findAllActive();

    List<Appointment> findAllCancelled();

    List<Appointment> findAllEnded();

}
