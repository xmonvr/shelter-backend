package pl.shelter.shelterbackend.Adopter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.shelter.shelterbackend.Address.Address;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Adopter {
    private String firstName;
    private String lastName;
    private Long id;
    private String phoneNumber;
    private String email;
    private Address address;
    private LocalDate dateOfBirth;

    public Adopter(String firstName, String lastName, String phoneNumber, String email, Address address, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }
}
