package pl.shelter.shelterbackend.animal.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

    Optional<Image> findFileByAnimalId(Long animalId);
    void deleteByAnimalId(Long animalId);
}
