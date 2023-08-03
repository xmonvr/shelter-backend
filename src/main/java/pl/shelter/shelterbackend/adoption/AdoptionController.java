package pl.shelter.shelterbackend.adoption;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adoptions")
@AllArgsConstructor
public class AdoptionController {

    AdoptionService adoptionService;

    @PostMapping("/adoption-form-pdf")
    public void createAdoptionForm(@RequestBody AdoptionForm adoptionForm, @RequestParam Long animalId) {
        adoptionService.preparePdfToDownload(animalId, adoptionForm);
    }

    @PostMapping("/send-adoption-form")
    public void sendAdoptionForm(@RequestBody AdoptionForm adoptionForm, @RequestParam Long animalId) {
        adoptionService.preparePdfToSend(animalId, adoptionForm);
    }

}
