package br.com.patitas.app.service.impl;

import br.com.patitas.app.enums.Specialization;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.VetCreationDTO;
import br.com.patitas.app.model.dto.VetUpdateDTO;
import br.com.patitas.app.repository.VetRepository;
import br.com.patitas.app.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

//foi 100% os acertivos
@SpringBootTest
class VetServiceImplTest {

	public static final String NAME = "pedro";
	public static final Specialization CLINICIAN = Specialization.CLINICIAN;
	public static final long ID = 1L;
	@Autowired
	private VetServiceImpl vetService;
	@MockBean
	private VetRepository repository;

	private Vet vet;
	private VetCreationDTO vetCreationDTO;
	private VetUpdateDTO vetUpdateDTO;

	private Optional<Vet> optionalVet;
	private List<Vet> vetList;

	@BeforeEach
	void setUp() {
		when(repository.save(any(Vet.class))).thenAnswer(invocation -> {
			Vet vet = invocation.getArgument(0);
			if (vet.getId() == null) {
				vet.setId(ID);
			}
			return vet;
		});
		testVet();
	}


	@Test
	void createVet() {
		when(repository.save(any())).thenReturn(vet);

		Vet vet = vetService.createVet(vetCreationDTO);

		assertNotNull(vet);
		assertEquals(Vet.class, vet.getClass());
		assertEquals(NAME, vet.getName());
		assertEquals(CLINICIAN, vet.getSpecialization());
		assertEquals(ID, vet.getId());

	}

	@Test
	void createPetEx() {
		when(repository.save(any())).thenReturn(vet);
		when(repository.findById(anyLong())).thenReturn(optionalVet);

		try {
			optionalVet.get().setId(2L);
			vetService.createVet(vetCreationDTO);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}

	}


	@Test
	void findById() {
		when(repository.findById(anyLong())).thenReturn(optionalVet);

		Vet vet = vetService.findById(ID);

		assertNotNull(vet);
		assertEquals(Vet.class, vet.getClass());
	}

	@Test
	void findByIdEx() { // foi
		when(repository.save(any())).thenReturn(vet);
		when(repository.findById(anyLong())).thenReturn(optionalVet);

		try {
			optionalVet.get().setId(2L);
			vetService.createVet(vetCreationDTO);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}
	}

	@Test
	void findAll() {
		when(repository.findAll()).thenReturn(List.of(vet));

		List<Vet> vets = vetService.findAll();

		assertNotNull(vets);
		assertEquals(1, vets.size());
		assertEquals(ID, vets.get(0).getId());
		assertEquals(NAME, vets.get(0).getName());
		assertEquals(CLINICIAN, vets.get(0).getSpecialization());
		assertEquals(Vet.class, vets.get(0).getClass());
	}

	@Test
	void updateVetById() {
		when(repository.findById(anyLong())).thenReturn(optionalVet);
		when(repository.save(any())).thenReturn(vet);

		Vet vet = vetService.updateVetById(ID, vetUpdateDTO);

		assertNotNull(vet);
		assertEquals(Vet.class, vet.getClass());
		assertEquals(ID, vet.getId());
		assertEquals(NAME, vet.getName());
		assertEquals(CLINICIAN, vet.getSpecialization());
	}

	@Test
	void UpdatePetEx() {
		when(repository.save(any())).thenReturn(vet);
		when(repository.findById(anyLong())).thenReturn(optionalVet);
		try {
			optionalVet.get().setId(2l);
			vetService.createVet(vetCreationDTO);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}


	}

	@Test
	void deleteById() {
		when(repository.findById(anyLong())).thenReturn(optionalVet);
		doNothing().when(repository).deleteById(anyLong());
		vetService.deleteById(ID);
		verify(repository, times(1)).deleteById(anyLong());
	}

	@Test
	void deleteByIdEX() {
		when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Not Found"));
		try {
			vetService.deleteById(ID);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}
	}

	@Test
	void saveVet() {
		when(repository.save(any())).thenReturn(vet);
		Vet vet = vetService.saveVet(new Vet(ID, NAME, CLINICIAN, new HashSet<>(), new HashSet<>()));

		assertNotNull(vet);
		assertEquals(Vet.class, vet.getClass());
		assertEquals(ID, vet.getId());
		assertEquals(NAME, vet.getName());
		assertEquals(CLINICIAN, vet.getSpecialization());

	}

	@Test
	void saveVetEX() {
		when(repository.save(any())).thenReturn(vet);
		when(repository.findById(anyLong())).thenReturn(optionalVet);
		try {
			optionalVet.get().setId(2l);
			vetService.saveVet(vet);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}
	}


	private void testVet() {
		vetCreationDTO = new VetCreationDTO(NAME, CLINICIAN);
		vetUpdateDTO = new VetUpdateDTO(NAME, CLINICIAN);
		vet = new Vet(ID, NAME, CLINICIAN, new HashSet<>(), new HashSet<>());
		optionalVet = Optional.of(new Vet(ID, NAME, CLINICIAN, new HashSet<>(), new HashSet<>()));
		vetList = new ArrayList<>(List.of(new Vet(ID, NAME, CLINICIAN, new HashSet<>(), new HashSet<>())));
	}
}