package pl.shelter.shelterbackend.user.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
