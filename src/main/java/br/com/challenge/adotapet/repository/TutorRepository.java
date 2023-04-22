package br.com.challenge.adotapet.repository;

import br.com.challenge.adotapet.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Optional<Tutor> findById(Long id);

    void deleteById(Long id);

    Optional<Tutor> findByEmail(String email);
}
