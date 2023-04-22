package br.com.challenge.adotapet.model.DTO;

import br.com.challenge.adotapet.model.Tutor;
import br.com.challenge.adotapet.repository.TutorRepository;

public class GetTutorDTO {

    private String name;
    private String email;
    private String city;
    private String aboutMe;
    private String tutorPhotoUrl;

    public GetTutorDTO() {
    }

    public GetTutorDTO(Tutor tutor) {
        this.name = tutor.getName();
        this.email = tutor.getEmail();
        this.city = tutor.getCity();
        this.aboutMe = tutor.getAboutMe();
        this.tutorPhotoUrl = tutor.getTutorPhotoUrl();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getTutorPhotoUrl() {
        return tutorPhotoUrl;
    }

    public void setTutorPhotoUrl(String tutorPhotoUrl) {
        this.tutorPhotoUrl = tutorPhotoUrl;
    }
}
