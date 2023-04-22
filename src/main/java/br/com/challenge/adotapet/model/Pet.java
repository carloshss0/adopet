package br.com.challenge.adotapet.model;

import jakarta.persistence.*;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String petName;
    private String petPhotoUrl;
    private Integer age;
    private PetSize size;
    private PetSpecie specie;
    private String temper;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetPhotoUrl() {
        return petPhotoUrl;
    }

    public void setPetPhotoUrl(String petPhotoUrl) {
        this.petPhotoUrl = petPhotoUrl;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public PetSize getSize() {
        return size;
    }

    public void setSize(PetSize size) {
        this.size = size;
    }

    public PetSpecie getSpecie() {
        return specie;
    }

    public void setSpecie(PetSpecie specie) {
        this.specie = specie;
    }

    public String getTemper() {
        return temper;
    }

    public void setTemper(String temper) {
        this.temper = temper;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }
}
