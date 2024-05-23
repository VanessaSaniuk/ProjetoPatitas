package br.com.patitas.app.controller;

import br.com.patitas.app.enums.Specialization;
import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.ScheduleCreationDTO;
import br.com.patitas.app.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ScheduleControllerTest {

	public static final Specialization CLINICIAN = Specialization.CLINICIAN;
	public static final String NAME = "pedro";
	public static final long ID = 1l;

	@InjectMocks
	private ScheduleController controller;

	@Mock
	private ScheduleService service;

	private Schedule schedule;
	private Vet vet;
	private Instant start;
	private Instant end;
	private ScheduleCreationDTO dto;

	private Optional<Schedule> optionalSchedule;
	private List<Schedule> scheduleList;
	private ResponseEntity<Schedule> response;


	@BeforeEach
	void setUp() {
		test();
	}


	@Test
	void postSchedule() {
		when(service.createSchedule(any())).thenReturn(schedule);
		response = controller.postSchedule(dto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void getById() {
		when(service.findById(anyLong())).thenReturn(schedule);

		response = controller.getById(ID);

		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(ResponseEntity.class, response.getClass());

	}

	@Test
	void getAllById() {
		when(service.findAll()).thenReturn(List.of(schedule));

		ResponseEntity<List<Schedule>> sc = controller.getAllById();

		assertNotNull(sc);
		assertNotNull(sc.getBody());
		assertEquals(ResponseEntity.class, sc.getClass());
		assertEquals(Schedule.class, sc.getBody().get(0).getClass());

	}

	@Test
	void deleteById() {

		doNothing().when(service).deleteById(anyLong());
		ResponseEntity<Void> sc = controller.deleteById(ID);
		assertNotNull(sc);
		assertNotNull(ResponseEntity.class, String.valueOf(sc.getClass()));
		verify(service, times(1)).deleteById(anyLong());
		assertEquals(HttpStatus.NO_CONTENT, sc.getStatusCode());
	}

	void test() {

		schedule = new Schedule(ID, start, end, vet, null);
		vet = new Vet(ID, NAME, CLINICIAN, new HashSet<>(), new HashSet<>());
		dto = new ScheduleCreationDTO(start, end, ID);
		scheduleList = new ArrayList<>(List.of(new Schedule(ID, start, end, vet, null)));

		optionalSchedule = Optional.of(new Schedule(ID, start, end, vet, null));

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
}