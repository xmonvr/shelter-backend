package pl.shelter.shelterbackend.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Buyer {
    private String email;
    private String firstName;
    private String lastName;
    private String language;
}
