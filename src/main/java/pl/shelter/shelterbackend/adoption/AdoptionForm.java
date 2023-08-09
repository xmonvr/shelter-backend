package pl.shelter.shelterbackend.adoption;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@ToString
public class AdoptionForm {

    Adopter adopter;
    Map<String, String> questions;
}
