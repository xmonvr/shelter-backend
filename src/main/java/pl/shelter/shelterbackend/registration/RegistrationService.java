package pl.shelter.shelterbackend.registration;

import pl.shelter.shelterbackend.user.User;
import pl.shelter.shelterbackend.user.UserRole;
import pl.shelter.shelterbackend.user.AppUserService;
import pl.shelter.shelterbackend.email.EmailSender;
import pl.shelter.shelterbackend.registration.token.ConfirmationToken;
import pl.shelter.shelterbackend.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        //token ktory wysylamy
        String token = appUserService.
                signUpUser(new User(request.getFirstName(), request.getLastName(), request.getEmail(),
                        request.getPassword(),/* request.getConfirmPassword(),*/ request.getBirthDate(), UserRole.USER));

        String link= "http://localhost:8081/api/registration/confirm?token=" + token;

        emailSender.send(request.getEmail(), buildEmail(request.getFirstName(), link));

        return token;
    }

    public String registerAdmin(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        //token ktory wysylamy
        String token = appUserService.
                signUpUser(new User(request.getFirstName(), request.getLastName(), request.getEmail(),
                        request.getPassword(),/* request.getConfirmPassword(),*/ request.getBirthDate(), UserRole.ADMIN));

        String link= "http://localhost:8081/api/registration/confirm?token=" + token;

        emailSender.send(request.getEmail(), buildEmail(request.getFirstName(), link));

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getUser().getEmail());
        return "Potwierdzono rejestrację! :)";
    }

    private String buildEmail(String name, String link) {
        return "<html>\n" +
                "<head>\n" +
                "  <title>Potwierdzenie rejestracji</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <h1>Potwierdzenie rejestracji</h1>\n" +
                "  <p>Cześć " + name + "! Dziękujemy za rejestrację na naszej stronie!</p>\n" +
                "  <p>Potwierdź rejestrację klikając w link:</p>\n" +
                "  <p><a href=\""+ link +"\">Aktywuj</a></p>\n" +
                "  <p>Pozdrawiamy,</p>\n" +
                "  <p>Schronisko</p>\n" +
                "</body>\n" +
                "</html>";
    }
}



























