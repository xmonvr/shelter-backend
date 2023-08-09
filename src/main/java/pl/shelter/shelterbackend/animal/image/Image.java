package pl.shelter.shelterbackend.animal.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Image {
    @Id     // primary key encji
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;
    @Lob
    @Column(name = "data")
    private byte[] data;
    @Column(name = "image_name")
    private String imageName;
    @Column(name = "animal_id")
    private Long animalId;
}
