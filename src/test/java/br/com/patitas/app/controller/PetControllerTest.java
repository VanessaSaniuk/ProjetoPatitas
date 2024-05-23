package br.com.patitas.app.controller;

import br.com.patitas.app.enums.Species;
import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.dto.PetCreationDTO;
import br.com.patitas.app.model.dto.PetUpdateDTO;
import br.com.patitas.app.repository.PetRepository;
import br.com.patitas.app.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
class PetControllerTest {

	public static final Integer AGE = 5;
	public static final String RACE = "sianesa";
	public static final Species CAT = Species.CAT;
	public static final String NAME = "morgana";
	public static final long ID = 1L;
	public static final Species DOG = Species.DOG;

	@InjectMocks
	private PetController controller;
	@Mock
	private PetService service;
	@Mock
	private PetRepository repository;


	private Pet pet;
	private PetCreationDTO petCreationDTO;
	private List<Pet> pets;
	private PetUpdateDTO petUpdateDTO;
	private Optional<Pet> optionalPet;
	private ResponseEntity<Pet> petResponse;


	@BeforeEach
	void setUp() {
		testPet();
	}

	@Test
	void postPet() { // pendente
		when(service.createPet(any())).thenReturn(pet);
		petResponse = controller.postPet(petCreationDTO);
		assertEquals(HttpStatus.CREATED, petResponse.getStatusCode());

	}

	@Test
	void getById() { //foi
		when(service.findById(anyLong())).thenReturn(pet);

		petResponse = controller.getById(ID);

		assertNotNull(petResponse);
		assertNotNull(petResponse.getBody());
		assertEquals(ResponseEntity.class, petResponse.getClass());
	}


	@Test
	void getAllById() { //foi
		when(service.findAll()).thenReturn(List.of(pet));

		ResponseEntity<List<Pet>> pet = controller.getAllById();

		assertNotNull(pet);
		assertNotNull(pet.getBody());
		assertEquals(ResponseEntity.class, pet.getClass());
		assertEquals(Pet.class, pet.getBody().get(0).getClass());
	}

	@Test
	void updateById() { //foi
		when(service.updatePetById(ID, petUpdateDTO)).thenReturn(pet);

		ResponseEntity<Pet> pet = controller.updateById(ID, petUpdateDTO);

		assertNotNull(pet);
		assertNotNull(pet.getBody());
		assertEquals(ResponseEntity.class, pet.getClass());

	}

	@Test
	void deleteById() { //foi
		doNothing().when(service).deleteById(anyLong());
		ResponseEntity<Void> pet = controller.deleteById(ID);
		assertNotNull(pet);
		assertNotNull(ResponseEntity.class, String.valueOf(pet.getClass()));
		verify(service, times(1)).deleteById(anyLong());
		assertEquals(HttpStatus.NO_CONTENT, pet.getStatusCode());

	}

	private void testPet() {
		pet = new Pet(ID, NAME, CAT, RACE, AGE, new HashSet<>());
		petCreationDTO = new PetCreationDTO(NAME, CAT, RACE, AGE);
		optionalPet = Optional.of(new Pet(ID, NAME, CAT, RACE, AGE, new HashSet<>()));
		pets = new ArrayList<>(List.of(new Pet(ID, NAME, CAT, RACE, AGE, new HashSet<>())));
		petUpdateDTO = new PetUpdateDTO(NAME, CAT, RACE, AGE);
	}
}