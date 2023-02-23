package pl.shelter.shelterbackend.animal;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Animal {
    private String name;
    private String breed;
//    private String Species;       //zrobic do tego enuma jako dog, cat, other;
    @Id     // primary key encji
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_sequence")
    @SequenceGenerator(name = "animal_sequence", sequenceName = "animal_sequence", allocationSize = 1)
    @Column(name="animal_id")
    private Long id;
    private String chip_number;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean isVaccinated;

    public Animal(String name, String breed, String chip_number, Gender gender, Boolean isVaccinated) {
        this.name = name;
        this.breed = breed;
        this.chip_number = chip_number;
        this.gender = gender;
        this.isVaccinated = isVaccinated;
    }

    public Animal() {

    }
}
