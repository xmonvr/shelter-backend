package pl.shelter.shelterbackend.animal;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.shelter.shelterbackend.animal.image.Image;
import pl.shelter.shelterbackend.animal.image.ImageService;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AnimalService {

    private AnimalRepository animalRepository;
    private final ImageService imageService;

    public Animal createAnimal(Animal animal, MultipartFile image) {
        Animal newAnimal = animalRepository.save(animal);
        imageService.save(image, newAnimal.getId());
        return newAnimal;
    }

    public Animal getAnimalById(Long id) {
        return animalRepository.findById(id).orElseThrow();
    }

    public void updateAnimal(Long id, Animal animal, MultipartFile image) {
        Animal animalUpdate = animalRepository.findById(id).orElseThrow();
        if (animal.getName() != null) {
            animalUpdate.setName(animal.getName());
        }

        if (animal.getTypeOfAnimal() != null) {
            animalUpdate.setTypeOfAnimal(animal.getTypeOfAnimal());
        }

        if (animal.getChip_number() != null) {
            animalUpdate.setChip_number(animal.getChip_number());
        }

        if (animal.getGender() != null) {
            animalUpdate.setGender(animal.getGender());
        }

        if (animal.getIsVaccinated() != null) {
            animalUpdate.setIsVaccinated(animal.getIsVaccinated());
        }

        if (animal.getAge() != null) {
            animalUpdate.setAge(animal.getAge());
        }

        if (image != null) {
            Image imageUpadate = imageService.getFileByAnimalId(id);
            try {
                imageUpadate.setData(image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//        animalRepository.save(animalUpdate);
    }

    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
        imageService.deleteImageByAnimalId(id);
    }

    public List<Animal> findFilteredAnimals(Integer ageMin, Integer ageMax, Gender gender, TypeOfAnimal typeOfAnimal) {
        return animalRepository.filterAnimals(ageMin, ageMax, gender, typeOfAnimal);
    }
}
