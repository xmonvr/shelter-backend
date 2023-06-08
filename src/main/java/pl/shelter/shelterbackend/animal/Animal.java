package pl.shelter.shelterbackend.animal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Animal {
    private String name;
    @Enumerated(EnumType.STRING)
    private TypeOfAnimal typeOfAnimal;
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
    private int age;
    public Animal(String name, TypeOfAnimal typeOfAnimal, String chip_number, Gender gender, Boolean isVaccinated, int age) {
        this.name = name;
        this.typeOfAnimal = typeOfAnimal;
        this.chip_number = chip_number;
        this.gender = gender;
        this.isVaccinated = isVaccinated;
        this.age = age;
    }

    public Animal() {

    }
}
