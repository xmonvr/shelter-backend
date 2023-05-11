package pl.shelter.shelterbackend.registration;

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

    private final LocalDate birthDate;
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String confirmPassword;
    private final String email;
    private final boolean policy;
}
