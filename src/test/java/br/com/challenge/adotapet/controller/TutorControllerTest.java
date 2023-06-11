package br.com.challenge.adotapet.controller;

import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class TutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TutorRepository tutorRepository;

    @Test
    void testCreateTutor_Success() throws Exception {
        URI uri = new URI("/tutores");
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{")
                .append("\"name\": \"Carlos\", ")
                .append("\"email\": \"teste@email.com\",")
                .append("\"password\": \"123456\"")
                .append("}");

        String json = jsonBuilder.toString();

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testeCreateTutor_Fail() throws Exception {
        URI uri = new URI("/tutores");

        Tutor tutorExistente = new Tutor();
        tutorExistente.setId(1L);
        tutorExistente.setName("Carlos");
        tutorExistente.setEmail("teste@teste.com");
        tutorExistente.setPassword("123456");

        Mockito.when(tutorRepository.findByEmail(Mockito.anyString()))
                .thenReturn(Optional.of(tutorExistente));



        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{")
                .append("\"name\": \"Carlos\", ")
                .append("\"email\": \"teste@email.com\",")
                .append("\"password\": \"123456\"")
                .append("}");

        String json = jsonBuilder.toString();

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    public void shouldReturnTutorsListSize() throws Exception {
        // Criação de dois tutores no banco de dados
        Tutor tutor1 = new Tutor();
        tutor1.setId(1L);
        tutor1.setName("João");
        tutor1.setEmail("joao@teste.com");
        tutor1.setPassword("123456");

        Tutor tutor2 = new Tutor();
        tutor2.setId(2L);
        tutor2.setName("Maria");
        tutor2.setEmail("maria@teste.com");
        tutor2.setPassword("abcdef");

        List<Tutor> listaTutores = Arrays.asList(tutor1, tutor2);

        // Simulação do tutorRepository retornando a lista de tutores
        Mockito.when(tutorRepository.findAll()).thenReturn(listaTutores);

        URI uri = new URI("/tutores");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }


}

