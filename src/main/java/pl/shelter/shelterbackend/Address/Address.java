package pl.shelter.shelterbackend.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String id;
    private String street;
    private String houseNumber;
    private String city;
    private String zipCode;
    private String country;
}
