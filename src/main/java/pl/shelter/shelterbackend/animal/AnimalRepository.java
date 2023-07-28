package pl.shelter.shelterbackend.animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, String> {
    //defaultowo jparepo zawiera findAll(), finfById(id), save(T entity), deleteById(id);
    Animal findById(Long id);
    void deleteById(Long id);
    @Query("SELECT animal FROM Animal animal WHERE" +
    "(:ageMin is null OR animal.age >= :ageMin) AND" +
    "(:ageMax is null OR animal.age <= :ageMax) AND" +
    "(:gender is null OR animal.gender = :gender) AND" +
    "(:typeOfAnimal is null OR animal.typeOfAnimal = :typeOfAnimal)")
    List<Animal> filterAnimals(Integer ageMin, Integer ageMax, Gender gender, TypeOfAnimal typeOfAnimal);

    @Query("SELECT id FROM Animal")
    List<Long> findAllIds();

}
