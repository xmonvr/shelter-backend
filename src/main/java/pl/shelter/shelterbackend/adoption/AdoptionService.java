package pl.shelter.shelterbackend.adoption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.shelter.shelterbackend.animal.AnimalRepository;

import java.util.List;

@Service
public class AdoptionService {

    @Autowired
    AdoptionRepository adoptionRepository;
    @Autowired
    AnimalRepository animalRepository;
    //todo sprawidzic czy dziala
    public Adoption createAdoption(Adoption adoption) {
//        System.out.println(adoption.toString());
//        Long animalId = adoption.getAnimal().getId();
//        Animal animal = animalRepository.findById(animalId);
//        adoption.setAnimal(animal);
        return adoptionRepository.save(adoption);
    }

    public Adoption getAdoptionById(Long id) {
        return adoptionRepository.findById(id);
    }

    public Adoption updateAdoption(Adoption adoption) {
        return adoptionRepository.save(adoption);
    }

    public void deleteAdoption(Long id) {
        adoptionRepository.deleteById(id);
    }

    public List<Adoption> getAllAdoptions() {
        return adoptionRepository.findAll();
    }
}
