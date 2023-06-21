package br.com.challenge.adotapet.controller;



import br.com.challenge.adotapet.dto.CreateTutorDTO;
import br.com.challenge.adotapet.dto.ViewTutorDTO;
import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.service.TutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {TutorController.class})
public class TutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorService tutorService;

    @Autowired
    private TutorController tutorController;


    @Nested
    @DisplayName("Tests for create endpoint")
    class CreateEndPoint {
        @Test
        @DisplayName("Create Tutor with Success and return 201")
        public void createTutor_Success() throws Exception {
            CreateTutorDTO mockCreateDTO = new CreateTutorDTO();
            mockCreateDTO.setName("Carlos");
            mockCreateDTO.setEmail("carlos@email.com");
            mockCreateDTO.setPassword("12345");

            Tutor tutor = mockCreateDTO.convert();
            tutor.setId(1L);

//            ViewTutorDTO mockViewDTO = new ViewTutorDTO(tutor);


            Mockito.when(tutorService.verifyEmailTutor(any(CreateTutorDTO.class)))
                    .thenReturn(true);

            Mockito.when(tutorService.createTutor(any(CreateTutorDTO.class)))
                    .thenReturn(tutor);


            String json = new ObjectMapper().writeValueAsString(mockCreateDTO);

            String actualContent = mockMvc.perform(MockMvcRequestBuilders
                            .post("/tutores")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();

            ViewTutorDTO actualTutorDTO = new ObjectMapper().readValue(actualContent, ViewTutorDTO.class);

            Assertions.assertNotNull(actualTutorDTO);
            Assertions.assertEquals(actualTutorDTO.getId(), 1L);
            Assertions.assertEquals(actualTutorDTO.getName(), "Carlos");
            Assertions.assertEquals(actualTutorDTO.getEmail(), "carlos@email.com");
        }
    }

    @Nested
    @DisplayName("Test get Tutor by Id")
    public class FindTutorByIdEndPoint {

        @Test
        @DisplayName("Find Tutor by Id and return status 200")
        public void findTutorByIdAndReturn200() throws Exception {
            Tutor tutor = new Tutor();
            tutor.setId(1L);
            tutor.setName("John Doe");
            tutor.setEmail("john@email.com");
            tutor.setPassword("12345");

            ViewTutorDTO viewTutorDTO = new ViewTutorDTO(tutor);


            Mockito.when(tutorService.getTutor(1L))
                    .thenReturn(Optional.of(tutor));

            String actualContent = mockMvc.perform(MockMvcRequestBuilders
                    .get("/tutores/{id}", 1L))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            ViewTutorDTO actualViewTutor = new ObjectMapper().readValue(actualContent, ViewTutorDTO.class);

            Assertions.assertNotNull(actualViewTutor);
            Assertions.assertEquals(actualViewTutor.getId(),1L);
            Assertions.assertEquals(actualViewTutor.getName(), "John Doe");
            Assertions.assertEquals(actualViewTutor.getEmail(), "john@email.com");
        }
    }

}




