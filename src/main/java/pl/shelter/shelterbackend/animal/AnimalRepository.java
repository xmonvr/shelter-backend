package pl.shelter.shelterbackend.animal;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, String> {
    //defaultowo jparepo zawiera findAll(), finfById(id), save(T entity), deleteById(id);
    Animal findById(Long id);
    void deleteById(Long id);
}
