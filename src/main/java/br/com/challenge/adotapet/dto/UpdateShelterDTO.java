package br.com.challenge.adotapet.dto;

import br.com.challenge.adotapet.model.Shelter;
import br.com.challenge.adotapet.repository.ShelterRepository;
import jakarta.validation.constraints.NotBlank;

public class UpdateShelterDTO {

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    public UpdateShelterDTO() {
    }

    public UpdateShelterDTO(Shelter shelter) {
        this.city = shelter.getCity();
        this.state = shelter.getState();
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

    public Shelter update(Long id, ShelterRepository shelterRepository) {
        Shelter shelter = shelterRepository.getReferenceById(id);

        shelter.setCity(this.city);
        shelter.setState(this.state);

        return shelter;
    }

}
