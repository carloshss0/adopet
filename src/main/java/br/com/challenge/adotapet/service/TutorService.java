package br.com.challenge.adotapet.service;

import br.com.challenge.adotapet.model.DTO.CreateTutorDTO;
import br.com.challenge.adotapet.model.DTO.UpdateTutorDTO;
import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public Tutor updateTutor(Long id, UpdateTutorDTO updateTutorDTO) {
        Optional<Tutor> optional = tutorRepository.findById(id);
        if (optional.isPresent()) {
            Tutor tutor = updateTutorDTO.update(id, tutorRepository);
            return tutor;
        }
        return null;
    }

    public Tutor createTutor(CreateTutorDTO createTutorDTO) {

        Tutor tutor = createTutorDTO.convert();
        tutorRepository.save(tutor);
        return tutor;
    }

    public boolean verifyEmailTutor(CreateTutorDTO createTutorDTO) {
        String possibleEmailExists = createTutorDTO.getEmail();
        Optional<Tutor> possibleTutor = tutorRepository.findByEmail(possibleEmailExists);
        if (possibleTutor.isPresent()) return false;
        return true;

    }

    public List<Tutor> listAllTutors () {
        return tutorRepository.findAll();
    }

    public Optional<Tutor> getTutor(Long id) {
        return tutorRepository.findById(id);
    }

    public boolean deleteTutor(Long id) {
        Optional<Tutor> tutorOptional = tutorRepository.findById(id);

        if(tutorOptional.isPresent()) {
            tutorRepository.delete(tutorOptional.get());
            return true;
        }
        return false;
    }

}
