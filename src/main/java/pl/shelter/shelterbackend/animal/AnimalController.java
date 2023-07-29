package pl.shelter.shelterbackend.animal;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/animal")
@AllArgsConstructor
public class AnimalController {

    private AnimalService animalService;

    @PostMapping("/add-animal")
    public Animal createAnimal(@RequestParam TypeOfAnimal typeOfAnimal,
                               @RequestParam String chipNumber,
                               @RequestParam Gender gender,
                               @RequestParam Boolean isVaccinated,
                               @RequestParam String name,
                               @RequestParam int age,
                               @RequestParam String description,
                               @RequestParam("image") MultipartFile image) {
        Animal animal = new Animal(name,typeOfAnimal, chipNumber, gender, isVaccinated, age, description);
        return animalService.createAnimal(animal, image);
    }

    @GetMapping("/animal-by-id")
    public Animal getAnimalById(@RequestParam("id") Long id) {
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
    public void deleteAnimals(@RequestParam/*("id")*/ Long id) {
        animalService.deleteAnimal(id);
    }

//    @GetMapping("/animals-list")
//    public List<Animal> getAllAnimals() {
//        return animalService.getAllAnimals();
//    }

    @GetMapping("/filtered-animals")
    public List<Animal> getFilteredAnimals(@RequestParam @Nullable Integer ageMin, @RequestParam @Nullable Integer ageMax, @RequestParam @Nullable Gender gender,
                                           @RequestParam @Nullable TypeOfAnimal typeOfAnimal) {
        return animalService.findFilteredAnimals(ageMin, ageMax, gender, typeOfAnimal);
    }

//    @GetMapping("/animals-id")
//    public List<Long> getAnimalsId() {
//        return animalService.getAllIds();
//    }

}
