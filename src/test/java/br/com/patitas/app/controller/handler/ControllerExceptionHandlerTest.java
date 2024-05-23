package br.com.patitas.app.controller.handler;

import br.com.patitas.app.service.exceptions.AppointmentStatusException;
import br.com.patitas.app.service.exceptions.ResourceNotFoundException;
import br.com.patitas.app.service.exceptions.ScheduleAlreadyUsedException;
import br.com.patitas.app.service.exceptions.ScheduleDuplicatedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class ControllerExceptionHandlerTest { // foi 100%

    @InjectMocks
    private ControllerExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void runtimeExceptionHandler() {
        ResponseEntity<StandardError> erro = exceptionHandler.runtimeExceptionHandler(new RuntimeException("Internal Server Error"),new  MockHttpServletRequest());

        assertNotNull(erro);
        assertNotNull(erro.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, erro.getStatusCode());
        assertEquals(ResponseEntity.class, erro.getClass());
        assertEquals(StandardError.class, erro.getBody().getClass());
        assertEquals("Internal Server Error", erro.getBody().getError());

    }

    @Test
    void resourceNotFoundExceptionHandler() {

        ResponseEntity<StandardError> erro = exceptionHandler.resourceNotFoundExceptionHandler
                (new ResourceNotFoundException("Not Found"),new MockHttpServletRequest());

        assertNotNull(erro);
        assertNotNull(erro.getBody());
        assertEquals(HttpStatus.NOT_FOUND, erro.getStatusCode());
        assertEquals(ResponseEntity.class, erro.getClass());
        assertEquals(StandardError.class, erro.getBody().getClass());
        assertEquals("Not Found", erro.getBody().getError());
        assertEquals(404,erro.getBody().getStatus());
        assertNotEquals("/pet/5",erro.getBody().getPath());
        assertNotEquals(LocalDateTime.now(),erro.getBody().getTimestamp());



    }

    @Test
    void appointmentStatusExceptionHandler() {
        ResponseEntity<StandardError> erro = exceptionHandler.appointmentStatusExceptionHandler(new AppointmentStatusException("Forbidden"),new  MockHttpServletRequest());

        assertNotNull(erro);
        assertNotNull(erro.getBody());
        assertEquals(HttpStatus.FORBIDDEN, erro.getStatusCode());
        assertEquals(ResponseEntity.class, erro.getClass());
        assertEquals(StandardError.class, erro.getBody().getClass());
        assertEquals("Forbidden", erro.getBody().getError());
    }

    @Test
    void scheduleAlreadyUsedExceptionHandler() {
        ResponseEntity<StandardError> erro = exceptionHandler.scheduleAlreadyUsedExceptionHandler(new ScheduleAlreadyUsedException("Conflict"), new  MockHttpServletRequest());

        assertNotNull(erro);
        assertNotNull(erro.getBody());
        assertEquals(HttpStatus.CONFLICT, erro.getStatusCode());
        assertEquals(ResponseEntity.class, erro.getClass());
        assertEquals(StandardError.class, erro.getBody().getClass());
        assertEquals("Conflict", erro.getBody().getError());
    }

    @Test
    void scheduleDuplicatedExceptionHandler() {
        ResponseEntity<StandardError> erro = exceptionHandler.scheduleDuplicatedExceptionHandler(new ScheduleDuplicatedException("Conflict"), new  MockHttpServletRequest());

        assertNotNull(erro);
        assertNotNull(erro.getBody());
        assertEquals(HttpStatus.CONFLICT, erro.getStatusCode());
        assertEquals(ResponseEntity.class, erro.getClass());
        assertEquals(StandardError.class, erro.getBody().getClass());
        assertEquals("Conflict", erro.getBody().getError());
    }

    @Test
    void httpMessageNotReadableExceptionHandler() {
        ResponseEntity<StandardError> erro = exceptionHandler.httpMessageNotReadableExceptionHandler(new HttpMessageNotReadableException("Bad Request"),new  MockHttpServletRequest());
        assertNotNull(erro);
        assertNotNull(erro.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, erro.getStatusCode());
        assertEquals(ResponseEntity.class, erro.getClass());
        assertEquals(StandardError.class, erro.getBody().getClass());
        assertEquals("Bad Request", erro.getBody().getError());

    }
}