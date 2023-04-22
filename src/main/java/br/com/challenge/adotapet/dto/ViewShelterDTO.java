package br.com.challenge.adotapet.dto;

import br.com.challenge.adotapet.model.Shelter;

public class ViewShelterDTO {

    private String name;
    private String city;
    private String state;

    public ViewShelterDTO() {
    }

    public ViewShelterDTO(Shelter shelter) {
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
}
