package br.com.challenge.adotapet.model.DTO;

import br.com.challenge.adotapet.model.Tutor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class CreateTutorDTO {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;

    public CreateTutorDTO() {
    }

    public CreateTutorDTO(Tutor tutor) {
        this.name = tutor.getName();
        this.email = tutor.getEmail();
        this.password = tutor.getPassword();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Tutor convert() {

        // nas proximas etapas irei implementar criptografia nas senhas para aumentar a seguranca
//        BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder();
//        String encryptPassword = encrypt.encode(password);

        return new Tutor(this.name, this.email, this.password);

    }
}
