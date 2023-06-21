package br.com.challenge.adotapet.controller;



import br.com.challenge.adotapet.dto.CreateTutorDTO;
import br.com.challenge.adotapet.dto.UpdateTutorDTO;
import br.com.challenge.adotapet.dto.ViewTutorDTO;
import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.service.TutorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        @DisplayName("Create Tutor fail because already exists the e-mail in the database")
        @Test
        public void createTutorFailBecauseTheEmailAlreadyExists() throws Exception {
            CreateTutorDTO mockCreateDTO = new CreateTutorDTO();
            mockCreateDTO.setName("Carlos");
            mockCreateDTO.setEmail("carlos@email.com");
            mockCreateDTO.setPassword("12345");

            Mockito.when(tutorService.verifyEmailTutor(any(CreateTutorDTO.class)))
                    .thenReturn(false);

            String json = new ObjectMapper().writeValueAsString(mockCreateDTO);

            mockMvc.perform(MockMvcRequestBuilders.post("/tutores")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotAcceptable());
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

        @Test
        @DisplayName("Find Tutor by Id and return 404")
        public void findTutorByIdAndReturn404() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/tutores/{id}", 1L))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Tests for find all Tutors")
    public class FindAllTutorsEndPoint {

        @DisplayName("Find all tutors with success")
        @Test
        public void findAllTutorsSuccess() throws Exception {
            Tutor tutor1 = new Tutor();
            tutor1.setId(1L);
            tutor1.setName("John");
            tutor1.setEmail("john@email.com");
            tutor1.setPassword("123456");

            Tutor tutor2 = new Tutor();
            tutor2.setId(2L);
            tutor2.setName("Marie");
            tutor2.setEmail("marie@email.com");
            tutor2.setPassword("123456");

            List<Tutor> tutors = Arrays.asList(tutor1, tutor2);

            Mockito.when(tutorService.listAllTutors()).thenReturn(tutors);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/tutores"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2));
        }
    }

    @Nested
    @DisplayName("Tests for delete end point")
    class DeleteEndPoint {

        @DisplayName("Delete must return status 200 when delete")
        @Test
        public void deleteWithSuccessAndReturn200() throws Exception {

            Mockito.when(tutorService.deleteTutor(1L)).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders.delete("/tutores/{id}", 1L))
                    .andExpect(status().isOk());
        }

        @DisplayName("Delete must return status 404 when provides an invalid Id")
        @Test
        public void deleteFailAndReturn404() throws Exception {

            Mockito.when(tutorService.deleteTutor(2L)).thenReturn(false);

            mockMvc.perform(MockMvcRequestBuilders.delete("/tutores/{id}", 2L))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Tests of the update end point")
    public class UpdateEndPoint {

        @DisplayName("Must Update a Tutor successfully")
        @Test
        public void updateTutorSuccess() throws Exception {
            UpdateTutorDTO updateTutorDTO = new UpdateTutorDTO();
            updateTutorDTO.setName("John");
            updateTutorDTO.setCity("Kansas");
            updateTutorDTO.setTutorPhotoUrl("smile face :D");
            updateTutorDTO.setAboutMe("I'm a mock");

            Tutor tutor = new Tutor();
            tutor.setId(1L);
            tutor.setName(updateTutorDTO.getName());
            tutor.setEmail("john@email.com");
            tutor.setPassword("12345");
            tutor.setCity(updateTutorDTO.getCity());
            tutor.setTutorPhotoUrl(updateTutorDTO.getTutorPhotoUrl());
            tutor.setAboutMe(updateTutorDTO.getAboutMe());


            Mockito.when(tutorService.updateTutor(eq(1L), any(UpdateTutorDTO.class)))
                    .thenReturn(tutor);

            String json = new ObjectMapper().writeValueAsString(updateTutorDTO);

            String actualContent = mockMvc.perform(MockMvcRequestBuilders.put("/tutores/{id}", 1L)
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            ViewTutorDTO actualViewTutorDTO = new ObjectMapper().readValue(actualContent, ViewTutorDTO.class);

            Assertions.assertNotNull(actualViewTutorDTO);
            Assertions.assertEquals(actualViewTutorDTO.getId(), 1L);
            Assertions.assertEquals(actualViewTutorDTO.getName(), "John");
            Assertions.assertEquals(actualViewTutorDTO.getEmail(), "john@email.com");
            Assertions.assertEquals(actualViewTutorDTO.getCity(), "Kansas");
            Assertions.assertEquals(actualViewTutorDTO.getAboutMe(), "I'm a mock");
            Assertions.assertEquals(actualViewTutorDTO.getTutorPhotoUrl(), "smile face :D");

        }
    }



}




