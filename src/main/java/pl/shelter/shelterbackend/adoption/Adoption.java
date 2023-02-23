package pl.shelter.shelterbackend.adoption;

import lombok.*;
import pl.shelter.shelterbackend.Adopter.Adopter;
import pl.shelter.shelterbackend.animal.Animal;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "Adoption")
@Table(name = "adoption")
//to jest tak naprawde formularz przedadopcyjny, a nie adopcja, wiec zmienic nazwe
public class Adoption {
    @Column(name = "adoption_date", nullable = false, columnDefinition = "TEXT")
    private LocalDateTime adoptionDateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, columnDefinition = "TEXT")
    private Long id;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="animal_id")
    private Animal animal;

//    private Long animalId;
    @ManyToOne(cascade = {CascadeType.ALL})  //Adopter może mieć wiele Adoption
    @JoinColumn(name = "address_id")
    private Adopter adopter;

    public Adoption(LocalDateTime adoptionDateTime, Animal animal, Adopter adopter) {
        this.adoptionDateTime = adoptionDateTime;
        this.animal = animal;
        this.adopter = adopter;
    }
}
