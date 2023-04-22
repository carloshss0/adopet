package br.com.challenge.adotapet.service;

import br.com.challenge.adotapet.dto.CreateShelterDTO;
import br.com.challenge.adotapet.dto.UpdateShelterDTO;
import br.com.challenge.adotapet.model.Shelter;
import br.com.challenge.adotapet.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelterService {

    @Autowired
    ShelterRepository shelterRepository;

    public Shelter createShelter(CreateShelterDTO createShelterDTO) {

        Shelter shelter = createShelterDTO.convert();
        shelterRepository.save(shelter);
        return shelter;
    }

    public List<Shelter> listAllTutors() {
         return shelterRepository.findAll();
    }

    public Optional<Shelter> getShelter(Long id) {
        return shelterRepository.findById(id);
    }

    public Shelter updateShelter(Long id, UpdateShelterDTO updateShelterDTO) {
        Optional<Shelter> optional = shelterRepository.findById(id);
        if (optional.isPresent()) {
            Shelter shelter = updateShelterDTO.update(id, shelterRepository);
            return shelter;
        }
        return null;
    }

    public boolean deleteShelter(Long id) {
        Optional<Shelter> shelterOptional = shelterRepository.findById(id);

        if(shelterOptional.isPresent()) {
            shelterRepository.delete(shelterOptional.get());
            return true;
        }
        return false;
    }
}
