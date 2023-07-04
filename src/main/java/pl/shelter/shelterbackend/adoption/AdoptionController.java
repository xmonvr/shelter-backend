package pl.shelter.shelterbackend.adoption;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adoptions")
@AllArgsConstructor
public class AdoptionController {

    AdoptionService adoptionService;

    @PostMapping
    public Adoption createAdoption(@RequestBody Adoption adoption) {

        return adoptionService.createAdoption(adoption);
    }

    @GetMapping
    public Adoption getAdoptionById(@RequestParam Long id) {
        return adoptionService.getAdoptionById(id);
    }

    @PutMapping
    public Adoption updateAdoption(@RequestBody Adoption adoption) {
        return adoptionService.updateAdoption(adoption);
    }

    @DeleteMapping
    public void deleteAdoption(@RequestParam Long id) {
        adoptionService.deleteAdoption(id);
    }

    @GetMapping("/adoption-list")
    public List<Adoption> getAllAdoptions() {
        return adoptionService.getAllAdoptions();
    }

    @PostMapping("/adoption-form-pdf")
    public void createAdoptionForm(@RequestBody AdoptionForm adoptionForm, @RequestParam Long animalId) {
        adoptionService.preparePdfToDownload(animalId, adoptionForm);
    }

    @PostMapping("/send-adoption-form")
    public void sendAdoptionForm(@RequestBody AdoptionForm adoptionForm, @RequestParam Long animalId) {
        adoptionService.preparePdfToSend(animalId, adoptionForm);
    }


}
