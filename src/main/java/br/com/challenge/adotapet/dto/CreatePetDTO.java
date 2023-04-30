package br.com.challenge.adotapet.dto;

import br.com.challenge.adotapet.model.Pet;
import br.com.challenge.adotapet.model.Shelter;
import br.com.challenge.adotapet.repository.ShelterRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.util.Optional;


public class CreatePetDTO {


    @NotNull
    private Long shelter_id;

    @NotBlank
    private String petName;

    @NotBlank
    private String petPhotoUrl;

    @NotBlank
    private String age;

    @NotBlank
    private String size;

    @NotBlank
    private String specie;

    @NotBlank
    private String temper;

    public CreatePetDTO() {
    }

    public CreatePetDTO(Pet pet) {
        this.shelter_id = pet.getShelter().getId();
        this.petName = pet.getPetName();
        this.petPhotoUrl = pet.getPetPhotoUrl();
        this.age = pet.getAge();
        this.size = pet.getSize().name();
        this.specie = pet.getSpecie().name();
        this.temper = pet.getTemper();

    }

    public Long getShelter_id() {
        return shelter_id;
    }

    public void setShelter_id(Long shelter_id) {
        this.shelter_id = shelter_id;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getTemper() {
        return temper;
    }

    public void setTemper(String temper) {
        this.temper = temper;
    }

    public Pet convert(ShelterRepository shelterRepository) {
        Optional<Shelter> shelter = shelterRepository.findById(shelter_id);
        if(shelter.isPresent()) {
            return new Pet(petName, petPhotoUrl, age, size,
                    specie, temper, shelter.get());
        }
        return null;
    }
}
