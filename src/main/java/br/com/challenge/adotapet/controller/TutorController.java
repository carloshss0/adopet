package br.com.challenge.adotapet.controller;

import br.com.challenge.adotapet.repository.TutorRepository;
import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.model.TutorDTO;
import br.com.challenge.adotapet.service.TutorService;
import jakarta.transaction.Transactional;
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
    public ResponseEntity<TutorDTO> updateTutor (@PathVariable Long id, @RequestBody TutorDTO tutorDTO) {
        Tutor tutor = tutorservice.updateTutor(id, tutorDTO);
        if (tutor != null) {
            return ResponseEntity.ok(new TutorDTO(tutor));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TutorDTO> createTutor (@RequestBody TutorDTO tutorDTO, UriComponentsBuilder uriBuilder) {
        Tutor tutor = tutorservice.createTutor(tutorDTO);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(tutor.getId()).toUri();
        return ResponseEntity.created(uri).body(new TutorDTO(tutor));
    }

    @GetMapping()
    public ResponseEntity<?> listAllTutors () {
        List<Tutor> tutors = tutorservice.listAllTutors();

        if (tutors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não encontrado");
        }
        List<TutorDTO> tutorDTOs = tutors.stream()
                .map(tutor -> new TutorDTO(tutor))
                .collect(Collectors.toList());

        return ResponseEntity.ok(tutorDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorDTO> getTutor (@PathVariable Long id) {
        Optional<Tutor> tutor = tutorservice.getTutor(id);
        if (tutor.isPresent()) {
            tutorservice.deleteTutor(id);
            return ResponseEntity.ok(new TutorDTO(tutor.get()));
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
