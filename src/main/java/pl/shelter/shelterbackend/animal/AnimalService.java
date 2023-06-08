package pl.shelter.shelterbackend.animal;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public Animal createAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal getAnimalById(Long id) {
        return animalRepository.findById(id);
    }

    public Animal updateAnimal(Long id, Animal animal) {
        Animal animalUpdate = animalRepository.findById(id);
        animalUpdate.setName(animal.getName());
        animalUpdate.setTypeOfAnimal(animal.getTypeOfAnimal());
        animalUpdate.setChip_number(animal.getChip_number());
        animalUpdate.setGender(animal.getGender());
        animalUpdate.setIsVaccinated(animal.getIsVaccinated());
        animalUpdate.setAge(animal.getAge());
        return animalRepository.save(animalUpdate);
    }

    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public List<Animal> findFilteredAnimals(Integer ageMin, Integer ageMax, Gender gender, TypeOfAnimal typeOfAnimal) {
        return animalRepository.filterAnimals(ageMin, ageMax, gender, typeOfAnimal);
    }
}
