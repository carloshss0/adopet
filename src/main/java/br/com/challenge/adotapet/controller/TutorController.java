package br.com.challenge.adotapet.controller;


import br.com.challenge.adotapet.model.DTO.CreateTutorDTO;
import br.com.challenge.adotapet.model.DTO.GetTutorDTO;
import br.com.challenge.adotapet.model.DTO.UpdateTutorDTO;
import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.service.TutorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/tutores")
public class TutorController {

    @Autowired
    private TutorService tutorservice;

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UpdateTutorDTO> updateTutor (@PathVariable Long id, @RequestBody @Valid UpdateTutorDTO updateTutorDTO) {
        Tutor tutor = tutorservice.updateTutor(id, updateTutorDTO);
        if (tutor != null) {
            return ResponseEntity.ok(new UpdateTutorDTO(tutor));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createTutor (@RequestBody @Valid CreateTutorDTO createTutorDTO, UriComponentsBuilder uriBuilder) {
        if (tutorservice.verifyEmailTutor(createTutorDTO)) {
            Tutor tutor = tutorservice.createTutor(createTutorDTO);
            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(tutor.getId()).toUri();
            return ResponseEntity.created(uri).body(new CreateTutorDTO(tutor));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("E-mail já cadastrado");
        }

    }

    @GetMapping()
    public ResponseEntity<?> listAllTutors () {
        List<Tutor> tutors = tutorservice.listAllTutors();

        if (tutors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não encontrado");
        }
        List<GetTutorDTO> tutorDTOs = tutors.stream()
                .map(tutor -> new GetTutorDTO(tutor))
                .collect(Collectors.toList());

        return ResponseEntity.ok(tutorDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTutorDTO> getTutor (@PathVariable Long id) {
        Optional<Tutor> tutor = tutorservice.getTutor(id);
        if (tutor.isPresent()) {
            return ResponseEntity.ok(new GetTutorDTO(tutor.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteTutor (@PathVariable Long id) {
        if (tutorservice.deleteTutor(id)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Tutor deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tutor não encontrado");
    }

}
