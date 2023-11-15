package pl.shelter.shelterbackend.email;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async      //zeby nie blokowalo klienta
    public void prepareRegistrationMail(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Potwierdz swoją rejestrację");
            helper.setFrom("schronisko.kontakt@gmail.com");
            javaMailSender.send(mimeMessage);
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
