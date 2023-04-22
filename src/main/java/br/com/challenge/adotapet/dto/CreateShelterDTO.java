package br.com.challenge.adotapet.dto;

import br.com.challenge.adotapet.model.Shelter;
import jakarta.validation.constraints.NotBlank;

public class CreateShelterDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String state;

    public CreateShelterDTO() {
    }

    public CreateShelterDTO(Shelter shelter) {
        this.name = shelter.getName();
        this.city = shelter.getCity();
        this.state = shelter.getState();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Shelter convert() {
        return new Shelter(this.name, this.city, this.state);
    }

}
