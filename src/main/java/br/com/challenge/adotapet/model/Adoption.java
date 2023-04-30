package br.com.challenge.adotapet.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Pet pet;

    @OneToOne
    private Tutor tutor;
    private LocalDate dateOfAdoption;

    public Adoption() {
    }

    public Adoption(Pet pet, Tutor tutor) {
        this.pet = pet;
        this.pet.setAdotado(true);
        this.tutor = tutor;
        this.dateOfAdoption = LocalDate.now();
    }



    public Long getId() {
        return id;
    }

    public Pet getPet() {
        return pet;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public LocalDate getDateOfAdoption() {
        return dateOfAdoption;
    }
}
