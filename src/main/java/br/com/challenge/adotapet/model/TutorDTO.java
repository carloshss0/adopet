package br.com.challenge.adotapet.model;

import br.com.challenge.adotapet.repository.TutorRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class TutorDTO {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String city;

    @NotBlank
    private String aboutMe;

    public TutorDTO() {}

    public TutorDTO (Tutor tutor) {
        this.name = tutor.getName();
        this.email = tutor.getEmail();
        this.city = tutor.getCity();
        this.aboutMe = tutor.getAboutMe();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Tutor atualizar(Long id, TutorRepository tutorRepository) {
        Tutor tutor = tutorRepository.getReferenceById(id);

        tutor.setName(this.name);
        tutor.setEmail(this.email);
        tutor.setCity(this.city);
        tutor.setAboutMe(this.aboutMe);

        return tutor;
    }

    public Tutor convert() {
        Tutor tutor = new Tutor();
        tutor.setName(this.name);
        tutor.setEmail(this.email);
        tutor.setCity(this.city);
        tutor.setAboutMe(this.aboutMe);

        return tutor;
    }
}
