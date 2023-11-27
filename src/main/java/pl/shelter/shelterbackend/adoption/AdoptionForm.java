package pl.shelter.shelterbackend.adoption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@ToString
@Getter
public class AdoptionForm {

    private Adopter adopter;
    private Map<String, String> questions;
}
