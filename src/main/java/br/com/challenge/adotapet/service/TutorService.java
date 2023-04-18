package br.com.challenge.adotapet.service;

import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.model.TutorDTO;
import br.com.challenge.adotapet.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    public Tutor updateTutor(Long id, TutorDTO tutorDTO) {
        Optional<Tutor> optional = tutorRepository.findById(id);
        if (optional.isPresent()) {
            Tutor tutor = tutorDTO.atualizar(id, tutorRepository);
            return tutor;
        }
        return null;
    }

    public Tutor createTutor(TutorDTO tutorDTO) {
        Tutor tutor = tutorDTO.convert();
        tutorRepository.save(tutor);
        return tutor;
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
