package br.com.challenge.adotapet.service;

import br.com.challenge.adotapet.dto.CreateTutorDTO;
import br.com.challenge.adotapet.dto.UpdateTutorDTO;
import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.repository.TutorRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TutorServiceTest {

    @MockBean
    private TutorRepository tutorRepository;

    @Autowired
    private TutorService tutorService;

    @Test
    public void shouldGetTutor() {
        Tutor mockTutor = new Tutor();
        mockTutor.setId(1L);
        mockTutor.setName("John");
        mockTutor.setEmail("john@email.com");
        mockTutor.setCity("Haven City");
        mockTutor.setAboutMe("I'm just a mock");
        mockTutor.setTutorPhotoUrl("photooo");
        mockTutor.setPassword("123");

        Mockito.when(tutorRepository.findById(1L)).thenReturn(Optional.of(mockTutor));
        Optional<Tutor> result = tutorService.getTutor(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockTutor, result.get());

    }

    @Test
    public void shouldNotGetTutor() {
        Mockito.when(tutorRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Tutor> result = tutorService.getTutor(2L);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void shouldCreateTutor() {
        CreateTutorDTO createTutorDTO = new CreateTutorDTO();
        createTutorDTO.setName("John");
        createTutorDTO.setEmail("john@email.com");
        createTutorDTO.setPassword("1234");

        Tutor result = tutorService.createTutor(createTutorDTO);

        ArgumentCaptor<Tutor> tutorCaptor = ArgumentCaptor.forClass(Tutor.class);
        verify(tutorRepository).save(tutorCaptor.capture());

        Tutor savedTutor = tutorCaptor.getValue();
        Assertions.assertEquals("John", savedTutor.getName());
        Assertions.assertEquals("john@email.com", savedTutor.getEmail());
        Assertions.assertEquals("1234", savedTutor.getPassword());
        Assertions.assertEquals(savedTutor, result);

    }

    @Test
    public void shouldTriggerAConstraintViolation() {
        CreateTutorDTO createTutorDTO = new CreateTutorDTO();
        createTutorDTO.setName("John");
        createTutorDTO.setEmail("john");
        createTutorDTO.setPassword("1234");

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CreateTutorDTO>> violations = validator.validate(createTutorDTO);

        // Call the method being tested
        Tutor result = tutorService.createTutor(createTutorDTO);

        // Assertions
        Assertions.assertFalse(violations.isEmpty());

    }

    @Test
    public void shouldUpdateTutor() {
        Long tutorId = 1L;
        Tutor existingTutor = new Tutor("John", "john@email.com", "1234");
        existingTutor.setId(tutorId);
        UpdateTutorDTO updateTutorDTO = new UpdateTutorDTO();
        updateTutorDTO.setName("John Doe");
        updateTutorDTO.setTutorPhotoUrl("Photo");
        updateTutorDTO.setCity("Kansas");
        updateTutorDTO.setAboutMe("I'm a mock");

        Mockito.when(tutorRepository.findById(tutorId)).thenReturn(Optional.of(existingTutor));
        Mockito.when(tutorRepository.getReferenceById(tutorId)).thenReturn(existingTutor);
        Mockito.when(tutorRepository.save(existingTutor)).thenReturn(existingTutor);

        Tutor result = tutorService.updateTutor(tutorId, updateTutorDTO);

        Assertions.assertEquals(updateTutorDTO.getName(), result.getName());
        Assertions.assertEquals(updateTutorDTO.getCity(), result.getCity());
        Assertions.assertEquals(updateTutorDTO.getAboutMe(), result.getAboutMe());
        Assertions.assertEquals(updateTutorDTO.getTutorPhotoUrl(), result.getTutorPhotoUrl());
        Assertions.assertNotNull(result);

        // Verify that the repository methods were called correctly
        verify(tutorRepository, times(1)).findById(tutorId);
        verify(tutorRepository, times(1)).getReferenceById(tutorId);
    }

    @Test
    public void verifyEmailTutorSuccess() {
        CreateTutorDTO createTutorDTO = new CreateTutorDTO();
        createTutorDTO.setEmail("john@email.com");
        createTutorDTO.setName("John");
        createTutorDTO.setPassword("1234");

        Assertions.assertTrue(tutorService.verifyEmailTutor(createTutorDTO));
    }

    @Test
    public void verifyEmailTutorFail() {
        CreateTutorDTO createTutorDTO = new CreateTutorDTO();
        createTutorDTO.setEmail("john@email.com");
        createTutorDTO.setName("John");
        createTutorDTO.setPassword("1234");

        Tutor tutor = new Tutor();
        tutor.setId(1L);
        tutor.setName("John Smith");
        tutor.setEmail("john@email.com");
        tutor.setPassword("1234");

        Mockito.when(tutorRepository.findByEmail("john@email.com")).thenReturn(Optional.of(tutor));

        boolean emailVerified = tutorService.verifyEmailTutor(createTutorDTO);

        // Assert that the email verification fails
        Assertions.assertFalse(emailVerified);
    }

    @Test
    public void shouldDeleteTutor() {
        Tutor tutor = new Tutor();
        tutor.setId(1L);
        tutor.setName("John Smith");
        tutor.setEmail("john@email.com");
        tutor.setPassword("1234");

        Mockito.when(tutorRepository.findById(1L)).thenReturn(Optional.of(tutor));

        Assertions.assertTrue(tutorService.deleteTutor(1L));
    }

    @Test
    public void shouldNotDeleteTutor() {
        // Considering the case when does not exist the Tutor with the given ID.

        Mockito.when(tutorRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertFalse(tutorService.deleteTutor(1L));
    }




}
