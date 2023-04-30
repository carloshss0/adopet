package br.com.challenge.adotapet.service;

import br.com.challenge.adotapet.dto.CreateAdoptionDTO;
import br.com.challenge.adotapet.model.Adoption;
import br.com.challenge.adotapet.model.Pet;
import br.com.challenge.adotapet.repository.AdoptionRepository;
import br.com.challenge.adotapet.repository.PetRepository;
import br.com.challenge.adotapet.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdoptionService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    TutorRepository tutorRepository;

    @Autowired
    AdoptionRepository adoptionRepository;

    public Adoption createAdoption(CreateAdoptionDTO createAdoptionDTO) {

        Adoption adoption = createAdoptionDTO.convert(petRepository, tutorRepository);
        if (adoption != null) {
            adoptionRepository.save(adoption);
            return adoption;
        }
        return null;
    }


    public boolean deleteAdoption(Long id) {

        Optional<Adoption> adoption = adoptionRepository.findById(id);

        if (adoption.isPresent()) {
            adoptionRepository.delete(adoption.get());
            adoption.get().getPet().setAdotado(false);
            return true;
        }
        return false;
    }
}
