package pl.shelter.shelterbackend.Adopter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, String> {

    Adopter findById(Long id);

    void deleteById(Long id);
}
