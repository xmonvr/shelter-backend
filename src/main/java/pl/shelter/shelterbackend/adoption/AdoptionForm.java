package pl.shelter.shelterbackend.adoption;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.shelter.shelterbackend.Adopter.Adopter;

import java.util.Map;

@AllArgsConstructor
@ToString
public class AdoptionForm {

    Adopter adopter;
    Map<String, String> questions;
}
