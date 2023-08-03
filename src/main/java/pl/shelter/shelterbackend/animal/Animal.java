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
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "animals")
public class Animal {
    @Column(name = "animal_name")
    private String name;
    @Column(name = "type_of_animal")
    @Enumerated(EnumType.STRING)
    private TypeOfAnimal typeOfAnimal;
    @Id     // primary key encji
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_sequence")
    @SequenceGenerator(name = "animal_sequence", sequenceName = "animal_sequence", allocationSize = 1)
    @Column(name="animal_id")
    private Long id;
    @Column(name = "chip_number")
    private String chip_number;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "is_vaccinated")
    private Boolean isVaccinated;
    @Column(name = "age")
    private Integer age;
    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    public Animal(String name, TypeOfAnimal typeOfAnimal, String chip_number, Gender gender, Boolean isVaccinated, Integer age, String description) {
        this.name = name;
        this.typeOfAnimal = typeOfAnimal;
        this.chip_number = chip_number;
        this.gender = gender;
        this.isVaccinated = isVaccinated;
        this.age = age;
        this.description = description;
    }

    public Animal() {

    }
}
