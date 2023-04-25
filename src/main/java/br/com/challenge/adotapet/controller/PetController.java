package br.com.challenge.adotapet.controller;

import br.com.challenge.adotapet.dto.*;
import br.com.challenge.adotapet.model.Pet;
import br.com.challenge.adotapet.service.PetService;
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
@RequestMapping("/pets")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createPet (@RequestBody @Valid CreatePetDTO createPetDTO,
                                        UriComponentsBuilder uriComponentsBuilder) {
        Pet pet = petService.createPet(createPetDTO);
        if (pet != null) {
            URI uri = uriComponentsBuilder.path("/pets/{id}").buildAndExpand(pet.getId()).toUri();
            return ResponseEntity.created(uri).body(new CreatePetDTO(pet));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> listAllPets () {
        List<Pet> pets = petService.listAllPets();

        if (pets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não encontrado");
        }
        List<ViewPetDTO> petsDTO = pets.stream()
                .map(pet -> new ViewPetDTO(pet))
                .collect(Collectors.toList());

        return ResponseEntity.ok(petsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPet(@PathVariable Long id) {
        Optional<Pet> pet = petService.getPet(id);

        if(pet.isPresent()) {
            return ResponseEntity.ok(new ViewPetDTO(pet.get()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não encontrado");

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updatePet(@RequestBody @Valid UpdatePetDTO updatePetDTO,
                                       @PathVariable Long id) {
        Pet pet = petService.updatePet(id, updatePetDTO);

        if (pet != null) {
            return ResponseEntity.ok(new UpdatePetDTO(pet));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletePet(@PathVariable Long id) {
        if (petService.deletePet(id)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Pet deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Pet não encontrado");

    }


}
