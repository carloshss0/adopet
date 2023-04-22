package br.com.challenge.adotapet.controller;

import br.com.challenge.adotapet.dto.*;
import br.com.challenge.adotapet.model.Shelter;
import br.com.challenge.adotapet.service.ShelterService;
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
@RequestMapping(value = "/abrigos")
public class ShelterController {

    @Autowired
    ShelterService shelterService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createShelter (@RequestBody @Valid CreateShelterDTO createShelterDTO,
                                            UriComponentsBuilder uriComponentsBuilder) {
        Shelter shelter = shelterService.createShelter(createShelterDTO);
        URI uri = uriComponentsBuilder.path("/abrigos/{id}").buildAndExpand(shelter.getId()).toUri();
        return ResponseEntity.created(uri).body(new CreateShelterDTO(shelter));

    }

    @GetMapping()
    public ResponseEntity<?> listAllShelters () {
        List<Shelter> shelters = shelterService.listAllTutors();

        if (shelters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não encontrado");
        }
        List<ViewShelterDTO> shelterDTOS = shelters.stream()
                .map(shelter -> new ViewShelterDTO(shelter))
                .collect(Collectors.toList());

        return ResponseEntity.ok(shelterDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShelter(@PathVariable Long id) {
        Optional<Shelter> shelter = shelterService.getShelter(id);
        if (shelter.isPresent()) {
            return ResponseEntity.ok(new ViewShelterDTO(shelter.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não encontrado");
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateShelter(@PathVariable Long id,
                                    @RequestBody @Valid UpdateShelterDTO updateShelterDTO) {
        Shelter shelter = shelterService.updateShelter(id, updateShelterDTO);
        if (shelter != null) {
            return ResponseEntity.ok(new UpdateShelterDTO(shelter));
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteShelter(@PathVariable Long id) {
        if (shelterService.deleteShelter(id)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Abrigo deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Abrigo não encontrado");
    }
}
