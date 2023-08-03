package pl.shelter.shelterbackend.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Entity(name = "Address")
//@Table(name = "address")
public class Address {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Column(name = "id", nullable = false, columnDefinition = "TEXT")
    private String id;
//    @Column(name = "street", nullable = false, columnDefinition = "TEXT")
    private String street;

//    @Column(name = "house_number", nullable = false, columnDefinition = "TEXT")
    private String houseNumber;

//    @Column(name = "city", nullable = false, columnDefinition = "TEXT")
    private String city;

//    @Column(name = "zip_code", nullable = false, columnDefinition = "TEXT")
    private String zipCode;

//    @Column(name = "country", nullable = false, columnDefinition = "TEXT")
    private String country;
}
