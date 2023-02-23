package pl.shelter.shelterbackend.animal;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/animal")
@AllArgsConstructor
public class AnimalController {

    private AnimalService animalService;

    @PostMapping
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalService.createAnimal(animal);
    }

    @GetMapping
    public Animal getAnimalById(@RequestParam("id") Long id) {
        return animalService.getAnimalById(id);
    }

    @PutMapping
    public Animal updateAnimal(@RequestParam("id") Long id, @RequestBody Animal animal) {
        return animalService.updateAnimal(id, animal);
    }

    @DeleteMapping
    public void deleteAnimals(@RequestParam("id") Long id) {
        animalService.deleteAnimal(id);
    }

    @GetMapping("/animals-list")
    public List<Animal> getAllAnimals() {
        return animalService.getAllAnimals();
    }
}
