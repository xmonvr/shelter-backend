package pl.shelter.shelterbackend.Adopter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.shelter.shelterbackend.Address.Address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
//@Entity(name = "Adopter")
//@Table(name = "adopter"/*, uniqueConstraints = {@UniqueConstraint(name = "adopter_email_unique", columnNames = "email")}*/)
// na tabeli zostanie email zostanie umieszczone ograniczenie unikalności
public class Adopter {
    //klasa adoptera chyba nie bedzie potrzebowala controllera, bo formularze beda generowane z adoption
//    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

//    @Column(name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String lastName;

//    @Id
//    @SequenceGenerator(name = "adopter_sequence", sequenceName = "adopter_sequence", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adopter_sequence")
//    @Column(name = "id", updatable = false) //nie chcemy, zeby kolumne dalo sie updateowac
    private Long id;

//    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

//    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;       //ten mail nie musi byc unique

//    @Embedded
//    @Column(name = "address", nullable = false)
//    @OneToOne
//    @JoinColumn(name = "address_id")
    private Address address;

//    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    public Adopter(String firstName, String lastName, String phoneNumber, String email, Address address, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }
}
