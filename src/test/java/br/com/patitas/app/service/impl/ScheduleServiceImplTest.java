package br.com.patitas.app.service.impl;

import br.com.patitas.app.enums.Specialization;
import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.ScheduleCreationDTO;
import br.com.patitas.app.model.dto.VetCreationDTO;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

// foi 100% do acertivo
@SpringBootTest
class ScheduleServiceImplTest {
	public static final Specialization CLINICIAN = Specialization.CLINICIAN;
	public static final String NAME = "pedro";
	public static final long ID = 1l;


	@Autowired
	private ScheduleServiceImpl service;
	@MockBean
	private ScheduleRepository repository;
	@MockBean
	private VetRepository vetRepository;
	@Mock
	private VetServiceImpl vetService;

	private Optional<Vet> optionalVet;
	private Vet vet;
	private Instant start;
	private Instant end;
	private VetCreationDTO vetCreationDTO;
	private ScheduleCreationDTO creationDTO;
	private List<Schedule> scheduleList;
	private Optional<Schedule> scheduleOptional;
	private Schedule schedule;

	@BeforeEach
	void setUp() {
		when(vetRepository.save(any(Vet.class))).thenAnswer(invocation -> {
			Vet vet = invocation.getArgument(0);
			if (vet.getId() == null) {
				vet.setId(ID);
			}
			return vet;
		});

		when(repository.save(any(Schedule.class))).thenAnswer(invocation -> {
			Schedule schedule = invocation.getArgument(0);
			if (schedule.getId() == null) {
				schedule.setId(ID);
			}
			return schedule;
		});

		testSchedule();

	}


	@Test
	void createSchedule() { //foi
		when(vetRepository.save(any())).thenReturn(vet);
		when(vetRepository.findById(anyLong())).thenReturn(optionalVet);
		when(repository.save(any())).thenReturn(schedule);

		Schedule schedule1 = service.createSchedule(creationDTO);

		assertNotNull(schedule1);
		assertEquals(Schedule.class, schedule1.getClass());
	}

	@Test
	void createScEx() {
		when(vetRepository.save(any())).thenReturn(vet);
		when(vetRepository.findById(anyLong())).thenReturn(optionalVet);
		when(repository.save(any())).thenReturn(schedule);
		when(repository.findById(anyLong())).thenReturn(scheduleOptional);

		try {
			scheduleOptional.get().setId(2l);
			service.createSchedule(creationDTO);

		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());

		}
	}

	@Test
	void findById() { //foi
		when(repository.findById(anyLong())).thenReturn(scheduleOptional);
		Schedule schedule1 = service.findById(ID);
		assertNotNull(schedule1);
		assertEquals(Schedule.class, schedule1.getClass());
	}

	@Test
	void findByIdEx() { // foi
		when(repository.save(any())).thenReturn(schedule);
		when(repository.findById(anyLong())).thenReturn(scheduleOptional);

		try {
			scheduleOptional.get().setId(2L);
			service.createSchedule(creationDTO);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}
	}


	@Test
	void findAll() { //foi
		when(repository.findAll()).thenReturn(List.of(schedule));
		List<Schedule> schedules = service.findAll();

		assertNotNull(schedules);
		assertEquals(1, schedules.size());
		assertEquals(ID, schedules.get(0).getId());
		assertEquals(start, schedules.get(0).getStartTime());
		assertEquals(end, schedules.get(0).getEndTime());
		assertEquals(vet, schedules.get(0).getVet());


	}

	@Test
	void deleteById() { //foi
		when(repository.findById(anyLong())).thenReturn(scheduleOptional);
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


	void start() {
		ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
		LocalDate date = LocalDate.now();
		LocalTime starte = LocalTime.of(10, 0);
		ZonedDateTime startZone = ZonedDateTime.of(date, starte, zoneId);
		this.start = startZone.toInstant();
	}

	void end() {
		ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
		LocalDate date = LocalDate.now();
		LocalTime ends = LocalTime.of(10, 30);
		ZonedDateTime endZone = ZonedDateTime.of(date, ends, zoneId);
		this.end = endZone.toInstant();

	}

	void testSchedule() {
		scheduleList = new ArrayList<>(List.of(new Schedule(ID, start, end, vet, null)));
		scheduleOptional = Optional.of(new Schedule(ID, start, end, vet, null));
		vet = new Vet(ID, NAME, CLINICIAN, new HashSet<>(), new HashSet<>());
		vetCreationDTO = new VetCreationDTO(NAME, CLINICIAN);
		optionalVet = Optional.of(new Vet(ID, NAME, CLINICIAN, new HashSet<>(), new HashSet<>()));
		schedule = new Schedule(ID, start, end, vet, null);
		creationDTO = new ScheduleCreationDTO(start, end, ID);
	}
}