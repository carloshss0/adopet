package br.com.challenge.adotapet.controller;


import br.com.challenge.adotapet.dto.CreateTutorDTO;
import br.com.challenge.adotapet.dto.ViewTutorDTO;
import br.com.challenge.adotapet.dto.UpdateTutorDTO;
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
    public ResponseEntity<ViewTutorDTO> updateTutor (@PathVariable Long id, @RequestBody @Valid UpdateTutorDTO updateTutorDTO) {
        Tutor tutor = tutorservice.updateTutor(id, updateTutorDTO);
        if (tutor != null) {
            return ResponseEntity.ok(new ViewTutorDTO(tutor));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createTutor (@RequestBody @Valid CreateTutorDTO createTutorDTO, UriComponentsBuilder uriBuilder) {
        if (tutorservice.verifyEmailTutor(createTutorDTO)) {
            Tutor tutor = tutorservice.createTutor(createTutorDTO);
            URI uri = uriBuilder.path("/tutores/{id}").buildAndExpand(tutor.getId()).toUri();
            return ResponseEntity.created(uri).body(new ViewTutorDTO(tutor));
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
        List<ViewTutorDTO> tutorDTOs = tutors.stream()
                .map(tutor -> new ViewTutorDTO(tutor))
                .collect(Collectors.toList());

        return ResponseEntity.ok(tutorDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewTutorDTO> getTutor (@PathVariable Long id) {
        Optional<Tutor> tutor = tutorservice.getTutor(id);
        if (tutor.isPresent()) {
            return ResponseEntity.ok(new ViewTutorDTO(tutor.get()));
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
