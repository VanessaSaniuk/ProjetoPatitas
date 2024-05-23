package br.com.patitas.app.service.impl;

import br.com.patitas.app.enums.AppointmentStatus;
import br.com.patitas.app.enums.Specialization;
import br.com.patitas.app.enums.Species;
import br.com.patitas.app.model.Appointment;
import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.*;
import br.com.patitas.app.repository.AppointmentRepository;
import br.com.patitas.app.repository.PetRepository;
import br.com.patitas.app.repository.ScheduleRepository;
import br.com.patitas.app.repository.VetRepository;
import br.com.patitas.app.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
class AppointmentServiceImplTest {
	public static final Specialization CLINICIAN = Specialization.CLINICIAN;
	public static final String NAME = "pedro";
	public static final long ID = 1l;

	public static final Integer AGE = 5;
	public static final String RACE = "sianesa";
	public static final Species CAT = Species.CAT;
	public static final String NAMEG = "morgana";
	public static final AppointmentStatus ACTIVE = AppointmentStatus.ACTIVE;

	@Autowired
	AppointmentServiceImpl service;
	@Mock
	private ScheduleServiceImpl scheduleService;
	@MockBean
	private AppointmentRepository repository;
	@Mock
	private PetRepository petRepository;
	@Mock
	private VetRepository vetRepository;
	@Mock
	private ScheduleRepository scheduleRepository;


	private AppointmentCreationDTO creationDTO;
	private PetCreationDTO petCreationDTO;

	private VetCreationDTO vetCreationDTO;
	private AppointmentUpdateDTO updateDTO;

	private Appointment appointment;
	private Vet vet;
	private Pet pet;
	private Schedule schedule;

	private Set<Appointment> set;
	private Set<Schedule> scheduleSet;

	private Instant start;
	private Instant end;
	Instant futureDate = Instant.now().plus(1, ChronoUnit.DAYS);

	private List<Schedule> scheduleList;
	private List<Pet> pets;
	private List<Vet> vetList;
	private List<Appointment> appointmentsList;

	;
	private List<Appointment> appointmentsList2;
	private List<Appointment> appointmentsList3;
	private Optional<Pet> optionalPet;
	private Optional<Vet> optionalVet;
	private Optional<Schedule> optionalSchedule;
	private Optional<Appointment> optionalAppointment;


	private ScheduleCreationDTO scheduleCreationDTO;

	@BeforeEach
	void setUp() {
		when(petRepository.save(any(Pet.class))).thenAnswer(invocation -> {
			Pet pet = invocation.getArgument(0);
			if (pet.getId() == null) {
				pet.setId(1L);
			}
			return pet;
		});
		when(vetRepository.save(any(Vet.class))).thenAnswer(invocation -> {
			Vet vet = invocation.getArgument(0);
			if (vet.getId() == null) {
				vet.setId(ID);
			}
			return vet;
		});
		when(scheduleRepository.save(any(Schedule.class))).thenAnswer(invocation -> {
			Schedule schedule = invocation.getArgument(0);
			if (schedule.getId() == null) {
				schedule.setId(ID);
			}
			return schedule;
		});
		when(repository.save(any(Appointment.class))).thenAnswer(invocation -> {
			Appointment appointment = invocation.getArgument(0);
			if (appointment.getId() == null) {
				appointment.setId(ID);
			}
			return appointment;
		});


		test();
	}


//	@Test
//	void createAppointment() { // n consegui
//		when(petRepository.save(any())).thenReturn(pet);
//		when(petRepository.findById(anyLong())).thenReturn(optionalPet);
//
//		when(vetRepository.save(any())).thenReturn(vet);
//		when(vetRepository.findById(anyLong())).thenReturn(optionalVet);
//
//		when(scheduleRepository.save(any())).thenReturn(schedule);
//		when(scheduleService.findById(anyLong())).thenReturn(schedule);
//
//		when(repository.save(any())).thenReturn(appointment);
//
//		Schedule schedule = scheduleService.createSchedule(scheduleCreationDTO);
//		Appointment appointment = service.createAppointment(creationDTO);
//
//		assertNotNull(appointment);
//		assertEquals(Appointment.class, appointment.getClass());
//
//	}


	@Test
	void findById() { // foi
		when(repository.findById(anyLong())).thenReturn(optionalAppointment);
		Appointment appointment1 = service.findById(ID);
		assertNotNull(appointment1);

	}

	@Test
	void findByIdEX() {
		when(repository.save(any())).thenReturn(appointmentsList);
		when(repository.findById(anyLong())).thenReturn(optionalAppointment);
		try {
			optionalAppointment.get().setId(2L);
			service.createAppointment(creationDTO);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}
	}


	@Test
	void findAll() { // foi
		when(repository.findAll()).thenReturn(List.of(appointment));

		List<Appointment> appointments = service.findAll();

		assertNotNull(appointments);
		assertEquals(1, appointments.size());
	}


//	@Test
//	void updateById() {// n consegui
//		when(petRepository.findById(anyLong())).thenReturn(optionalPet);
//		when(petRepository.save(any())).thenReturn(pet);
//		when(vetRepository.save(any())).thenReturn(vet);
//		when(vetRepository.findById(anyLong())).thenReturn(optionalVet);
//		when(scheduleRepository.save(any())).thenReturn(schedule);
//		when(scheduleRepository.findById(anyLong())).thenReturn(optionalSchedule);
//
//		schedule = appointment.getSchedule();
//		Schedule scq = service.findValidSchedule(vet, start);
//		schedule.setAppointment(null);
//		appointment.setSchedule(null);
//		scheduleRepository.save(schedule);
//		appointment.setSchedule(scq);
//		when(repository.save(appointment));
//
//		Appointment upAP = service.updateById(ID, updateDTO);
//
//		assertNotNull(upAP);
//		verify(repository).save(any(Appointment.class));
//
//	}


	@Test
	void cancelAppointmentById() { // foi
		when(repository.findById(anyLong())).thenReturn(optionalAppointment);

		Appointment appointment1 = service.cancelAppointmentById(ID);

		assertNotNull(appointment1);
		assertEquals(Appointment.class, appointment1.getClass());
	}

	@Test
	void cancelAppointmentByIdEX() {
		when(repository.save(any())).thenReturn(appointmentsList);
		when(repository.findById(anyLong())).thenReturn(optionalAppointment);

		try {
			optionalAppointment.get().setId(2L);
			service.cancelAppointmentById(2l);

		} catch (Exception exception) {
			assertEquals(ClassCastException.class, exception.getClass());
		}
	}


	@Test
	void endAppointmentById() { // foi
		when(repository.findById(anyLong())).thenReturn(optionalAppointment);
		Appointment appointment1 = service.endAppointmentById(ID);
		assertNotNull(appointment1);
		assertEquals(Appointment.class, appointment1.getClass());
	}

	@Test
	void endAppointmentByIdEX() {
		when(repository.save(any())).thenReturn(appointmentsList);
		when(repository.findById(anyLong())).thenReturn(optionalAppointment);

		try {
			optionalAppointment.get().setId(2L);
			service.endAppointmentById(2l);

		} catch (Exception exception) {
			assertEquals(ClassCastException.class, exception.getClass());
		}
	}


	@Test
	void deleteById() { // foi
		when(repository.findById(anyLong())).thenReturn(optionalAppointment);
		doNothing().when(repository).deleteById(anyLong());
		service.deleteById(ID);
		verify(repository, times(1)).deleteById(anyLong());
	}

	@Test
	void deleteByIdEX() {
		when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Not Found"));
		try {
			service.deleteById(ID);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}
	}

	@Test
	void findAllActive() { // foi
		when(repository.findAll()).thenReturn(appointmentsList);
		List<Appointment> appointment = service.findAllActive();
		assertNotNull(appointment);
		assertEquals(Appointment.class, appointment.get(0).getClass());
		assertEquals(1, appointment.size());

	}

	@Test
	void findAllCancelled() { // foi
		when(repository.findAll()).thenReturn(appointmentsList2);
		List<Appointment> appointment = service.findAllCancelled();
		assertNotNull(appointment);
		assertEquals(Appointment.class, appointment.get(0).getClass());
		assertEquals(1, appointment.size());
	}

	@Test
	void findAllEnded() { // foi
		when(repository.findAll()).thenReturn(appointmentsList3);
		List<Appointment> appointment = service.findAllEnded();
		assertNotNull(appointment);
		assertEquals(Appointment.class, appointment.get(0).getClass());
		assertEquals(1, appointment.size());
	}

	@Test
	void countSpecialization() { // foi
		when(vetRepository.save(any())).thenReturn(vet);
		when(repository.findAll()).thenReturn(appointmentsList);
		Map<Specialization, Long> appointments = service.countSpecialization();
		assertNotNull(appointments);
		assertEquals(1, appointments.size());
	}

	public Instant start() {
		ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
		LocalDate date = LocalDate.of(2024, 5, 22);
		LocalTime starts = LocalTime.of(20, 0);
		ZonedDateTime startZone = ZonedDateTime.of(date, starts, zoneId);
		return this.start = startZone.toInstant();
	}

	public Instant end() {
		ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
		LocalDate date = LocalDate.of(2024, 5, 22);
		LocalTime ends = LocalTime.of(20, 30);
		ZonedDateTime endsZone = ZonedDateTime.of(date, ends, zoneId);
		return this.end = endsZone.toInstant();
	}


	void test() {
		petCreationDTO = new PetCreationDTO(NAME, CAT, RACE, AGE);
		vetCreationDTO = new VetCreationDTO(NAME, CLINICIAN);
		creationDTO = new AppointmentCreationDTO(1L, ID, futureDate);
		scheduleCreationDTO = new ScheduleCreationDTO(start, end, ID);

		updateDTO = new AppointmentUpdateDTO(futureDate);

		pet = new Pet(ID, NAMEG, CAT, RACE, AGE, set);
		vet = new Vet(ID, NAME, CLINICIAN, scheduleSet, set);

		schedule = new Schedule(ID, start, end, vet, appointment);
		appointment = new Appointment(ID, pet, vet, schedule, ACTIVE);

		optionalPet = Optional.of(new Pet(ID, NAMEG, CAT, RACE, AGE, new HashSet<>()));
		optionalVet = Optional.of(new Vet(ID, NAME, CLINICIAN, new HashSet<>(), new HashSet<>()));
		optionalSchedule = Optional.of(new Schedule(ID, start, end, vet, null));
		optionalAppointment = Optional.of(new Appointment(ID, pet, vet, schedule, ACTIVE));

		pets = new ArrayList<>(List.of(new Pet(ID, NAME, CAT, RACE, AGE, new HashSet<>())));
		vetList = new ArrayList<>(List.of(new Vet(ID, NAME, CLINICIAN, new HashSet<>(), new HashSet<>())));
		scheduleList = new ArrayList<>(List.of(new Schedule(ID, start, end, vet, null)));
		appointmentsList = new ArrayList<>(List.of(new Appointment(ID, pet, vet, schedule, ACTIVE)));
		appointmentsList2 = new ArrayList<>(List.of(new Appointment(ID, pet, vet, schedule, AppointmentStatus.CANCELLED)));
		appointmentsList3 = new ArrayList<>(List.of(new Appointment(ID, pet, vet, schedule, AppointmentStatus.ENDED)));
		set = Set.of(new Appointment(ID, pet, vet, schedule, ACTIVE));
		scheduleSet = Set.of(new Schedule(ID, start, end, vet, appointment));
	}

}