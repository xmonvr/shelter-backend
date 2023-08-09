package pl.shelter.shelterbackend.adoption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private String houseNumber;
    private String city;
    private String zipCode;
    private String country;
}
