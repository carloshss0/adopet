package br.com.challenge.adotapet.dto;

import br.com.challenge.adotapet.model.Adoption;
import br.com.challenge.adotapet.model.Pet;
import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.repository.PetRepository;
import br.com.challenge.adotapet.repository.TutorRepository;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public class CreateAdoptionDTO {

    @NotNull
    private Long tutor_id;

    @NotNull
    private Long pet_id;


    public CreateAdoptionDTO() {
    }

    public CreateAdoptionDTO(Adoption adoption) {
        this.tutor_id = adoption.getPet().getId();
        this.pet_id = adoption.getPet().getId();
    }

    public Long getTutor_id() {
        return tutor_id;
    }

    public Long getPet_id() {
        return pet_id;
    }

    public Adoption convert (PetRepository petRepository,
                             TutorRepository tutorRepository) {
        Optional<Pet> pet = petRepository.findById(pet_id);
        Optional<Tutor> tutor = tutorRepository.findById(tutor_id);

        if(pet.isPresent() && tutor.isPresent()) {
            return new Adoption(pet.get(), tutor.get());
        }
        return null;
    }

}
