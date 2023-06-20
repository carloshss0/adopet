package br.com.challenge.adotapet.controller;


import br.com.challenge.adotapet.dto.CreateTutorDTO;
import br.com.challenge.adotapet.service.TutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;


@ExtendWith(SpringExtension.class)
@WebMvcTest(TutorController.class)
class TutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TutorService tutorService;

    @Test
    void createTutor_Success() throws Exception {
        CreateTutorDTO createTutorDTO = new CreateTutorDTO();
        createTutorDTO.setEmail("john@example.com");
        createTutorDTO.setName("John");
        createTutorDTO.setPassword("1234");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/tutores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTutorDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // Other test methods...

}



