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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.shelter.shelterbackend.animal.Animal;
import pl.shelter.shelterbackend.animal.AnimalRepository;
import pl.shelter.shelterbackend.animal.TypeOfAnimal;

import javax.activation.DataHandler;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.activation.DataSource;
import java.io.*;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdoptionService {
    private final AnimalRepository animalRepository;
    private final JavaMailSender javaMailSender;

    public String convertToString(Long animalId, AdoptionForm adoptionForm) {
        Animal animal = animalRepository.findById(animalId).orElseThrow();

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

        String personalPartString = "FORMULARZ PRZEDADOPCYJNY\n\nDANE OSOBY STARAJĄCEJ SIĘ O ADOPCJĘ: \nImię: " + adoptionForm.getAdopter().getFirstName() +
                "\nNazwisko: " + adoptionForm.getAdopter().getLastName() + "\nAdres email: " + adoptionForm.getAdopter().getEmail() +
                "\nNumer telefonu: " + adoptionForm.getAdopter().getPhoneNumber() + "\nData urodzenia: " + adoptionForm.getAdopter().getDateOfBirth().toString() +
                "\nUlica: " + adoptionForm.getAdopter().getAddress().getStreet() + "\nNumer domu/mieszkania: " + adoptionForm.getAdopter().getAddress().getHouseNumber() +
                "\nMiasto: " + adoptionForm.getAdopter().getAddress().getCity() + "\nKod pocztowy: " + adoptionForm.getAdopter().getAddress().getZipCode() +
                "\nKraj: " + adoptionForm.getAdopter().getAddress().getCountry() +
                "\n\nDANE ZWIERZĘCIA:\nImię:" + animal.getName() +
                "\nTyp: " + typeOfAnimal +
                "\nNumer chip: " + animal.getChip_number() +
                "\nPłeć: " + animal.getGender() +
                "\nSzczepienia: " + isVaccinated +
                "\nWiek: " + animal.getAge() +
                "\n\nANKIETA:\n";

        stringBuilder.append(personalPartString);

        int i = 1;
        for (Map.Entry<String, String> entry : adoptionForm.getQuestions().entrySet()) {
            String questionsString = i + ". " + entry.getKey() + "\n " + entry.getValue() + "\n\n";
            i++;
            stringBuilder.append(questionsString);
        };

        return stringBuilder.toString();
    }

    public Document preparePdfToDownload(Long animalId, AdoptionForm adoptionForm) {
        String userHome = System.getProperty("user.home");
        String folder = "Downloads";
        String documentName = "formularz";
        String documentFormat = ".pdf";

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(userHome + "\\" + folder + "\\" + documentName + documentFormat);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return createPdf(animalId, adoptionForm, outputStream);
    }

    public void preparePdfToSend(Long animalId, AdoptionForm adoptionForm) {
        String documentName = "formularz.pdf";
        String from = "schronisko.kontakt@gmail.com";
        String to = "schronisko.kontakt@gmail.com";
        String subject = "Formularz przedadopcyjny";

        ByteArrayOutputStream byteArrayOutputStream =  new ByteArrayOutputStream();

        try {
            createPdf(animalId, adoptionForm, byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();
            MimeBodyPart emailTextBodyPart = new MimeBodyPart();
            emailTextBodyPart.setText("Formularz przedadopcyjny w załączniku.");
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName(documentName);
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(emailTextBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(from);
            mimeMessage.setContent(mimeMultipart);
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Document createPdf(Long animalId, AdoptionForm adoptionForm, OutputStream outputStream) {
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
            document.open();
            BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
            document.add(new Paragraph(convertToString(animalId, adoptionForm), new Font(baseFont)));
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }

        document.addCreationDate();
        document.close();
        return document;
    }
}