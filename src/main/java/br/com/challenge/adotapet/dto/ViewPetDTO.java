package br.com.challenge.adotapet.dto;

import br.com.challenge.adotapet.model.Pet;


public class ViewPetDTO {

    private Long shelter_id;
    private String petName;
    private String petPhotoUrl;
    private String age;
    private String size;
    private String specie;
    private String temper;
    private String address;

    public ViewPetDTO() {
    }

    public ViewPetDTO(Pet pet) {

        this.shelter_id = pet.getShelter().getId();
        this.petName = pet.getPetName();
        this.petPhotoUrl = pet.getPetPhotoUrl();
        this.age = pet.getAge();
        this.size = pet.getSize().name();
        this.specie = pet.getSpecie().name();
        this.temper = pet.getTemper();
        this.address = pet.getShelter().getCity() + ", " + pet.getShelter().getState();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
