package br.com.challenge.adotapet.repository;

import br.com.challenge.adotapet.model.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
}
