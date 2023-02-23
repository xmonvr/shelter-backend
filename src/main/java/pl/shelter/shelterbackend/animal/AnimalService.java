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
        animalUpdate.setBreed(animal.getBreed());
        animalUpdate.setChip_number(animal.getChip_number());
        animalUpdate.setGender(animal.getGender());
        animalUpdate.setIsVaccinated(animal.getIsVaccinated());
        return animalRepository.save(animalUpdate);
    }

    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }
}
