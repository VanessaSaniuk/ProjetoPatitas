package br.com.patitas.app.controller;

import br.com.patitas.app.enums.AppointmentStatus;
import br.com.patitas.app.enums.Specialization;
import br.com.patitas.app.enums.Species;
import br.com.patitas.app.model.Appointment;
import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.*;
import br.com.patitas.app.repository.AppointmentRepository;
import br.com.patitas.app.service.AppointmentService;
import jakarta.persistence.Access;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentControllerTest {

    public static final Specialization CLINICIAN = Specialization.CLINICIAN;
    public static final String NAME = "pedro";
    public static final long ID = 1l;

    public static final Integer AGE = 5;
    public static final String RACE = "sianesa";
    public static final Species CAT = Species.CAT;
    public static final String NAMEG = "morgana";
    public static final AppointmentStatus ACTIVE = AppointmentStatus.ACTIVE;


    @InjectMocks
    private AppointmentController controller;
    @Mock
    private AppointmentService service;



    private Appointment appointment; private Pet pet; private Schedule schedule; private Vet vet;

    private Set<Appointment> set;  private Set<Schedule> scheduleSet;
    private Instant start; private Instant end; private Instant sc;
    private List<Appointment>appointmentsList;  private List<Appointment>appointmentsList2;   private List<Appointment>appointmentsList3;
    private AppointmentCreationDTO creationDTO;  private ScheduleCreationDTO scheduleCreationDTO;

    private AppointmentUpdateDTO updateDTO; private AppointmentReportDTO reportDTO;
    private ResponseEntity<Appointment> response;


    @BeforeEach
    void setUp(){
        test();

    }


    @Test
    void postAppointment() {
        when(service.createAppointment(any())).thenReturn(appointment);
        response = controller.postAppointment(creationDTO);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());

    }

    @Test
    void getById() { //foi
        when(service.findById(anyLong())).thenReturn(appointment);

        response = controller.getById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class,response.getClass());
    }

    @Test
    void getAllById() {// foi
        when(service.findAll()).thenReturn(List.of(appointment));

        ResponseEntity<List<Appointment>> appointment = controller.getAllById();

        assertNotNull(appointment);
        assertNotNull(appointment.getBody());
        assertEquals(ResponseEntity.class,appointment.getClass());
        assertEquals(Appointment.class,appointment.getBody().get(0).getClass());
    }



    @Test
    void appointmentsReport() { // foi
        when(service.findAllActive()).thenReturn(appointmentsList);
        when(service.findAllCancelled()).thenReturn(appointmentsList2);
        when(service.findAllEnded()).thenReturn(appointmentsList3);

        List<Appointment> activeAppointments = service.findAllActive();
        List<Appointment>endedAppointments = service.findAllEnded();
        List<Appointment>cancelledAppointments = service.findAllCancelled();

        Long active = (long) activeAppointments.size();
        Long ended = (long) endedAppointments.size();
        Long cancelled = (long) cancelledAppointments.size();
        Map<Specialization,Long> cont = service.countSpecialization();

       ResponseEntity<AppointmentReportDTO> reportDTO1 = controller.appointmentsReport();

        assertNotNull(reportDTO1);
        assertEquals(ResponseEntity.class, reportDTO1.getClass());


    }

    @Test
    void updateById() {//foi
        when(service.updateById(ID,updateDTO)).thenReturn(appointment);

        response = controller.updateById(ID,updateDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class,response.getClass());
    }

    @Test
    void cancelById() { //foi
        when(service.cancelAppointmentById(ID)).thenReturn(appointment);
        response = controller.cancelById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class,response.getClass());
    }

    @Test
    void endById() { //foi
        when(service.endAppointmentById(anyLong())).thenReturn(appointment);

        response = controller.endById(ID);

        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class,response.getClass());
    }

    @Test
    void deleteById() { //foi
        doNothing().when(service).deleteById(anyLong());
        ResponseEntity<Void> response1= controller.deleteById(ID);
        assertNotNull(response1);
        assertNotNull(ResponseEntity.class, String.valueOf(vet.getClass()));
        verify(service, times(1)).deleteById(anyLong());
        assertEquals(HttpStatus.NO_CONTENT, response1.getStatusCode());
    }

    void start(){
        ZoneId zoneId =ZoneId.of("America/Sao_Paulo");
        LocalDate date = LocalDate.of(2024,5,18);
        LocalTime starts = LocalTime.of(20,0);
        ZonedDateTime startZone = ZonedDateTime.of(date,starts,zoneId);
        this.start=startZone.toInstant();
    }
    void end(){
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDate date = LocalDate.of(2024,5,18);
        LocalTime ends = LocalTime.of(20,30);
        ZonedDateTime endsZone = ZonedDateTime.of(date,ends,zoneId);
        this.end=endsZone.toInstant();
    }
    void test(){
        pet = new Pet(ID,NAMEG, CAT,RACE,AGE,set);
        vet = new Vet(ID,NAME, CLINICIAN,scheduleSet,set);
        schedule = new Schedule(ID,start,end,vet,appointment);
        appointment = new Appointment(ID,pet,vet,schedule, ACTIVE);


        appointmentsList = new ArrayList<>(List.of(new Appointment(ID,pet,vet,schedule,ACTIVE)));
        appointmentsList2 = new ArrayList<>(List.of(new Appointment(ID,pet,vet,schedule,AppointmentStatus.CANCELLED)));
        appointmentsList3 = new ArrayList<>(List.of(new Appointment(ID,pet,vet,schedule,AppointmentStatus.ENDED)));

        creationDTO= new AppointmentCreationDTO(ID,ID,sc);
        scheduleCreationDTO = new ScheduleCreationDTO(start,end,ID);

        updateDTO = new AppointmentUpdateDTO(sc);


    }
}