package pl.shelter.shelterbackend.adoption;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.shelter.shelterbackend.animal.Animal;
import pl.shelter.shelterbackend.animal.AnimalRepository;
import pl.shelter.shelterbackend.animal.TypeOfAnimal;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdoptionService {

    @Autowired
    private AdoptionRepository adoptionRepository;
    @Autowired
    private AnimalRepository animalRepository;
    private final JavaMailSender mailSender;

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

    public String convertToString(Long animalId, AdoptionForm adoptionForm) {
        Animal animal = animalRepository.findById(animalId);

        String isVaccinated;
        if (animal.getIsVaccinated()) {
            isVaccinated =  "Tak";
        } else {
            isVaccinated = "Nie";
        }

        String typeOfAnimal;
        if (animal.getTypeOfAnimal().equals(TypeOfAnimal.DOG)) {
            typeOfAnimal = "Pies";
        } else if (animal.getTypeOfAnimal().equals(TypeOfAnimal.CAT)) {
            typeOfAnimal = "Kot";
        } else {
            typeOfAnimal = "Inne";
        }

        StringBuilder stringBuilder = new StringBuilder();

        String personalPartString = "FORMULARZ PRZEDADOPCYJNY\n\nDANE OSOBY STARAJĄCEJ SIĘ O ADOPCJĘ: \nImię: " + adoptionForm.adopter.getFirstName() +
                "\nNazwisko: " + adoptionForm.adopter.getLastName() + "\nAdres email: " + adoptionForm.adopter.getEmail() +
                "\nNumer telefonu: " + adoptionForm.adopter.getPhoneNumber() + "\nData urodzenia: " + adoptionForm.adopter.getDateOfBirth().toString() +
                "\nUlica: " + adoptionForm.adopter.getAddress().getStreet() + "\nNumer domu/mieszkania: " + adoptionForm.adopter.getAddress().getHouseNumber() +
                "\nMiasto: " + adoptionForm.adopter.getAddress().getCity() + "\nKod pocztowy: " + adoptionForm.adopter.getAddress().getZipCode() +
                "\nKraj: " + adoptionForm.adopter.getAddress().getCountry() +
                "\n\nDANE ZWIERZĘCIA:\nImię:" + animal.getName() +
                "\nTyp: " + typeOfAnimal +
                "\nNumer chip: " + animal.getChip_number() +
                "\nPłeć: " + animal.getGender() +
                "\nSzczepienia: " + isVaccinated +
                "\nWiek: " + animal.getAge() +
                "\n\nANKIETA:\n";

        stringBuilder.append(personalPartString);

/*        adoptionForm.questions.entrySet().forEach(a -> {
            int i = 1;
            String questionsString = "\nPytanie nr " + i + "\n" + a.getKey() + "\n " + a.getValue();
            stringBuilder.append(questionsString);
            i++;
        });*/

        int i = 1;
        for (Map.Entry<String, String> entry : adoptionForm.questions.entrySet()) {
            String questionsString = i + ". " + entry.getKey() + "\n " + entry.getValue() + "\n\n";
            i++;
            stringBuilder.append(questionsString);
        };

        return stringBuilder.toString();
    }

    //https://api.itextpdf.com/iText5/java/5.5.9/com/itextpdf/text/Document.html
    public Document preparePdfToDownload(Long animalId, AdoptionForm adoptionForm) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        String userHome = System.getProperty("user.home");
        String folder = "Downloads";
        String documentName = "formularz";
        String documentFormat = ".pdf";

        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(userHome + "\\" + folder + "\\" + documentName + documentFormat));
            log.info("path: " + userHome + "\\" + folder + "\\" + documentName + "\\" + documentFormat);
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        document.open();
        try {
            BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
            document.add(new Paragraph(convertToString(animalId, adoptionForm), new Font(baseFont)));
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
        document.addCreationDate();     //metadata
        document.close();

        return document;
    }

    public ByteArrayOutputStream preparePdfToSend(Long animalId, AdoptionForm adoptionForm) {
        ByteArrayOutputStream outputStream;
        outputStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        try {
            PdfWriter.getInstance(document, outputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.open();
        try {
            BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
            document.add(new Paragraph(convertToString(animalId, adoptionForm), new Font(baseFont)));
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
        document.addCreationDate();     //metadata
        document.close();
        return outputStream;
    }
}
