package pl.shelter.shelterbackend.animal;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.shelter.shelterbackend.animal.image.ImageService;

import java.util.List;

@RestController
@RequestMapping(path = "/animal")
@AllArgsConstructor
public class AnimalController {

    private AnimalService animalService;
    private final ImageService imageService;

    @GetMapping(value = "/get-image-by-animalId", produces = MediaType.IMAGE_JPEG_VALUE)
    @Transactional
    public byte[] getImage(@RequestParam Long animalId) {
        return imageService.getFileByAnimalId(animalId).getData();
    }

    @PostMapping("/add-animal")
    public Animal createAnimal(@RequestParam TypeOfAnimal typeOfAnimal,
                               @RequestParam String chipNumber,
                               @RequestParam Gender gender,
                               @RequestParam Boolean isVaccinated,
                               @RequestParam String name,
                               @RequestParam int age,
                               @RequestParam String description,
                               @RequestParam MultipartFile image) {
        Animal animal = new Animal(name,typeOfAnimal, chipNumber, gender, isVaccinated, age, description);
        return animalService.createAnimal(animal, image);
    }

    @GetMapping("/animal-by-id")
    public Animal getAnimalById(@RequestParam Long id) {
        return animalService.getAnimalById(id);
    }

    @PutMapping("/edit-animal")
    @Transactional
    public void updateAnimal(@RequestParam Long id,
                               @RequestParam @Nullable TypeOfAnimal typeOfAnimal,
                               @RequestParam @Nullable String chipNumber,
                               @RequestParam @Nullable Gender gender,
                               @RequestParam @Nullable Boolean isVaccinated,
                               @RequestParam @Nullable String name,
                               @RequestParam @Nullable Integer age,
                               @RequestParam @Nullable String description,
                               @RequestParam @Nullable MultipartFile image) {
        Animal animal = new Animal(name,typeOfAnimal, chipNumber, gender, isVaccinated, age, description);
        animalService.updateAnimal(id, animal, image);
    }

    @DeleteMapping("/delete-animal")
    @Transactional
    public void deleteAnimal(@RequestParam Long id) {
        animalService.deleteAnimal(id);
    }

    @GetMapping("/filtered-animals")
    public List<Animal> getFilteredAnimals(@RequestParam @Nullable Integer ageMin, @RequestParam @Nullable Integer ageMax, @RequestParam @Nullable Gender gender,
                                           @RequestParam @Nullable TypeOfAnimal typeOfAnimal) {
        return animalService.findFilteredAnimals(ageMin, ageMax, gender, typeOfAnimal);
    }
}
