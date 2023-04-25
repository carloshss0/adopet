package br.com.challenge.adotapet.service;

import br.com.challenge.adotapet.dto.CreatePetDTO;
import br.com.challenge.adotapet.dto.UpdatePetDTO;
import br.com.challenge.adotapet.model.Pet;
import br.com.challenge.adotapet.repository.PetRepository;
import br.com.challenge.adotapet.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    ShelterRepository shelterRepository;

    public Pet createPet(CreatePetDTO createPetDTO) {

        Pet pet = createPetDTO.convert(shelterRepository);
        if (pet != null) {
            petRepository.save(pet);
            return pet;
        }
        return null;
    }

    public List<Pet> listAllPets() {

        return petRepository.findAll();
    }

    public Optional<Pet> getPet(Long id) {
        return petRepository.findById(id);
    }

    public Pet updatePet(Long id, UpdatePetDTO updatePetDTO) {
        Optional<Pet> optional = petRepository.findById(id);
        if (optional.isPresent()) {
            Pet pet = updatePetDTO.update(id, petRepository);
            return pet;
        }
        return null;
    }

    public boolean deletePet(Long id) {
        Optional<Pet> pet = petRepository.findById(id);

        if (pet.isPresent()) {
            petRepository.delete(pet.get());
            return true;
        }
        return false;
    }
}
