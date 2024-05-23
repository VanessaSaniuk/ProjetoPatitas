package br.com.patitas.app.service.impl;

import br.com.patitas.app.enums.Species;
import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.dto.PetCreationDTO;
import br.com.patitas.app.model.dto.PetUpdateDTO;
import br.com.patitas.app.repository.PetRepository;
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


@SpringBootTest
//foi 100% os acertivos
class PetServiceImplTest {

	public static final Integer AGE = 5;
	public static final String RACE = "sianesa";
	public static final Species CAT = Species.CAT;
	public static final String NAME = "morgana";
	public static final long ID = 1L;
	public static final Species DOG = Species.DOG;

	@Autowired
	private PetServiceImpl petService;
	@MockBean
	private PetRepository repository;

	private Pet pet;
	private PetCreationDTO petCreationDTO;
	private PetUpdateDTO petUpdateDTO;
	private Optional<Pet> optionalPet;
	private List<Pet> pets;
	private Long Id;

	@BeforeEach
	void setUp() {
		when(repository.save(any(Pet.class))).thenAnswer(invocation -> {
			Pet pet = invocation.getArgument(0);
			if (pet.getId() == null) {
				pet.setId(ID);
			}
			return pet;
		});
		testPet();
	}

	@Test
	void createPet() { //foi
		when(repository.save(any())).thenReturn(pet);

		Pet pet = petService.createPet(petCreationDTO);

		assertNotNull(pet);
		assertNotNull(pet);
		assertEquals(Pet.class, pet.getClass());
		assertEquals(NAME, pet.getName());
		assertEquals(CAT, pet.getSpecies());
		assertEquals(RACE, pet.getRace());
		assertEquals(AGE, pet.getAge());


	}

	@Test
	void createPetEx() {
		when(repository.save(any())).thenReturn(pet);
		when(repository.findById(anyLong())).thenReturn(optionalPet);

		try {
			optionalPet.get().setId(2L);
			petService.createPet(petCreationDTO);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}

	}

	@Test
	void findById() { //foi
		when(repository.findById(anyLong())).thenReturn(optionalPet);
		Pet response = petService.findById(ID);

		assertNotNull(pet);
		assertEquals(Pet.class, response.getClass());
		assertEquals(NAME, pet.getName());
		assertEquals(CAT, pet.getSpecies());
		assertEquals(RACE, pet.getRace());
		assertEquals(AGE, pet.getAge());


	}

	@Test
	void findByIdEx() { // foi
		when(repository.save(any())).thenReturn(pet);
		when(repository.findById(anyLong())).thenReturn(optionalPet);

		try {
			optionalPet.get().setId(2L);
			petService.createPet(petCreationDTO);
		} catch (Exception exception) {
			assertEquals(RuntimeException.class, exception.getClass());
		}
	}

	@Test
	void findAll() { //foi
		when(repository.findAll()).thenReturn(List.of(pet));

		List<Pet> petsl = petService.findAll();

		assertNotNull(petsl);
		assertEquals(1, pets.size());
		assertEquals(ID, pets.get(0).getId());
		assertEquals(Pet.class, pets.get(0).getClass());
		assertEquals(NAME, pets.get(0).getName());
		assertEquals(AGE, pets.get(0).getAge());
		assertEquals(RACE, pets.get(0).getRace());

	}

	@Test
	void updatePetById() { //foi
		when(repository.save(any())).thenReturn(pet);
		when(repository.findById(anyLong())).thenReturn(optionalPet);

		Pet pet = petService.updatePetById(ID, petUpdateDTO);

		assertNotNull(pet);
		assertEquals(Pet.class, pet.getClass());
		assertEquals(NAME, pet.getName());
		assertEquals(CAT, pet.getSpecies());
		assertEquals(RACE, pet.getRace());
		assertEquals(AGE, pet.getAge());
	}

	@Test
	void UpdatePetEx() {
		when(repository.save(any())).thenReturn(pet);
		when(repository.findById(anyLong())).thenReturn(optionalPet);
		try {
			optionalPet.get().setId(2l);
			petService.createPet(petCreationDTO);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}


	}

	@Test
	void deleteById() { //foi
		when(repository.findById(anyLong())).thenReturn(optionalPet);
		doNothing().when(repository).deleteById(anyLong());
		petService.deleteById(ID);
		verify(repository, times(1)).deleteById(anyLong());

	}

	private void testPet() { //foi
		pet = new Pet(ID, NAME, CAT, RACE, AGE, new HashSet<>());
		petCreationDTO = new PetCreationDTO(NAME, CAT, RACE, AGE);
		optionalPet = Optional.of(new Pet(ID, NAME, CAT, RACE, AGE, new HashSet<>()));
		pets = new ArrayList<>(List.of(new Pet(ID, NAME, CAT, RACE, AGE, new HashSet<>())));
		petUpdateDTO = new PetUpdateDTO(NAME, DOG, RACE, AGE);
	}

	@Test
	void deleteByIdEX() {
		when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException("Not Found"));
		try {
			petService.deleteById(ID);
		} catch (Exception exception) {
			assertEquals(ResourceNotFoundException.class, exception.getClass());
		}
	}
}