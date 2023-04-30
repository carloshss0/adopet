package br.com.challenge.adotapet.controller;

import br.com.challenge.adotapet.dto.CreateAdoptionDTO;
import br.com.challenge.adotapet.model.Adoption;
import br.com.challenge.adotapet.service.AdoptionService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/adocao")
public class AdoptionController {

    @Autowired
    AdoptionService adoptionService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createAdoption (@RequestBody @Valid CreateAdoptionDTO createAdoptionDTO,
                                        UriComponentsBuilder uriComponentsBuilder) {
        Adoption adoption = adoptionService.createAdoption(createAdoptionDTO);
        if (adoption != null) {
            URI uri = uriComponentsBuilder.path("/adocao/{id}").buildAndExpand(adoption.getId()).toUri();
            return ResponseEntity.created(uri).body(new CreateAdoptionDTO(adoption));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteAdoption(@PathVariable Long id) {
        if (adoptionService.deleteAdoption(id)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Adoção deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Adoção não encontrada");

    }
}
