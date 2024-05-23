package br.com.patitas.app.controller;

import br.com.patitas.app.enums.Specialization;
import br.com.patitas.app.enums.Species;
import br.com.patitas.app.model.Pet;
import br.com.patitas.app.model.Schedule;
import br.com.patitas.app.model.Vet;
import br.com.patitas.app.model.dto.PetCreationDTO;
import br.com.patitas.app.model.dto.PetUpdateDTO;
import br.com.patitas.app.model.dto.VetCreationDTO;
import br.com.patitas.app.model.dto.VetUpdateDTO;
import br.com.patitas.app.service.PetService;
import br.com.patitas.app.service.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@SpringBootTest
class VetControllerTest {

    public static final Integer AGE = 5;
    public static final String RACE = "sianesa";
    public static final Species CAT = Species.CAT;
    public static final String NAME = "morgana";
    public static final long ID = 1L;
    public static final Species DOG = Species.DOG;
    public static final Specialization CLINICIAN = Specialization.CLINICIAN;


    @InjectMocks
    private VetController vetController;
    @Mock
    private VetService vetService;


    private Vet vet;
    private VetCreationDTO vetCreationDTO;
    private List<Vet> vetList;
    private ResponseEntity <List<Vet>> responseVet;
    private VetUpdateDTO vetUpdateDTO;
    private ResponseEntity<Vet> vetResponseEntity;

    @BeforeEach
    void setUp() {
       // MockitoAnnotations.openMocks(this);
        testVet();
    }

    @Test
    void postVet() {
        when(vetService.createVet(any())).thenReturn(vet);
       vetResponseEntity = vetController.postVet(vetCreationDTO);
       assertEquals(HttpStatus.CREATED,vetResponseEntity.getStatusCode());

    }

    @Test
    void getById() {
        when(vetService.findById(anyLong())).thenReturn(vet);

        vetResponseEntity = vetController.getById(ID);

       assertNotNull(vetResponseEntity);
       assertNotNull(vetResponseEntity.getBody());
       assertEquals(ResponseEntity.class,vetResponseEntity.getClass());
    }

    @Test
    void getAllById() {
        when(vetService.findAll()).thenReturn(List.of(vet));

        ResponseEntity<List<Vet>> vet11 = vetController.getAllById();

      assertNotNull(vet11);
      assertNotNull(vet11.getBody());
      assertEquals(ResponseEntity.class,vet11.getClass());
      assertEquals(Vet.class,vet11.getBody().get(0).getClass());
    }


    @Test
    void updateById() { // foi
        when(vetService.updateVetById(ID,vetUpdateDTO)).thenReturn(vet);

        ResponseEntity<Vet> vet = vetController.updateById(ID,vetUpdateDTO);

        assertNotNull(vet);
        assertNotNull(vet.getBody());
        assertEquals(ResponseEntity.class,vet.getClass());

    }

    @Test
    void deleteById() { //foi
        doNothing().when(vetService).deleteById(anyLong());
        ResponseEntity<Void> vet= vetController.deleteById(ID);
        assertNotNull(vet);
        assertNotNull(ResponseEntity.class, String.valueOf(vet.getClass()));
        verify(vetService, times(1)).deleteById(anyLong());
        assertEquals(HttpStatus.NO_CONTENT, vet.getStatusCode());

    }




    private void testVet(){
        vet = new Vet(ID,NAME,CLINICIAN, new HashSet<>(), new HashSet<>());
        vetCreationDTO = new VetCreationDTO(NAME,CLINICIAN);
        //  optionalVet = Optional.of(new Vet(ID,NAME,CLINICIAN ));
        vetList = new ArrayList<>(List.of(new Vet(ID,NAME,CLINICIAN, new HashSet<>(), new HashSet<>())));
        vetUpdateDTO =new VetUpdateDTO(NAME,CLINICIAN);
        Set<Schedule> scheduless = new HashSet<>();

    }

}