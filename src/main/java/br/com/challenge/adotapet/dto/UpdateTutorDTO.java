package br.com.challenge.adotapet.dto;

import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.repository.TutorRepository;
import jakarta.validation.constraints.NotBlank;


public class UpdateTutorDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String city;

    @NotBlank
    private String aboutMe;

    @NotBlank
    private String tutorPhotoUrl;

    public UpdateTutorDTO() {}

    public UpdateTutorDTO(Tutor tutor) {
        this.name = tutor.getName();
        this.city = tutor.getCity();
        this.aboutMe = tutor.getAboutMe();
        this.tutorPhotoUrl = tutor.getTutorPhotoUrl();
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getTutorPhotoUrl() {
        return tutorPhotoUrl;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCity(String city) {
        this.city = city;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setTutorPhotoUrl(String tutorPhotoUrl) {
        this.tutorPhotoUrl = tutorPhotoUrl;
    }

    public Tutor update(Long id, TutorRepository tutorRepository) {
        Tutor tutor = tutorRepository.getReferenceById(id);

        tutor.setName(this.name);
        tutor.setCity(this.city);
        tutor.setAboutMe(this.aboutMe);
        tutor.setTutorPhotoUrl(this.tutorPhotoUrl);

        return tutor;
    }

}
