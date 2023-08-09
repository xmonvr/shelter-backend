package pl.shelter.shelterbackend.adoption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adopter {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Address address;
    private LocalDate dateOfBirth;
}
