package br.com.challenge.adotapet.dto;

import br.com.challenge.adotapet.model.Pet;
import br.com.challenge.adotapet.model.PetSize;
import br.com.challenge.adotapet.repository.PetRepository;
import jakarta.validation.constraints.NotBlank;

public class UpdatePetDTO {

    @NotBlank
    private String petPhotoUrl;

    @NotBlank
    private String age;

    @NotBlank
    private String size;

    @NotBlank
    private String temper;

    public UpdatePetDTO() {
    }

    public UpdatePetDTO(Pet pet) {
        this.petPhotoUrl = pet.getPetPhotoUrl();
        this.age = pet.getAge();
        this.size = pet.getSize().name();
        this.temper = pet.getTemper();
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

    public String getTemper() {
        return temper;
    }

    public void setTemper(String temper) {
        this.temper = temper;
    }

    public Pet update(Long id, PetRepository petRepository) {
        Pet pet = petRepository.getReferenceById(id);

        pet.setPetPhotoUrl(petPhotoUrl);
        pet.setAge(age);
        pet.setSize(PetSize.valueOf(size));
        pet.setTemper(temper);

        return pet;
    }
}
