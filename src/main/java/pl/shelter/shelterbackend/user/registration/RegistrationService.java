package pl.shelter.shelterbackend.user.registration;

import pl.shelter.shelterbackend.email.EmailService;
import pl.shelter.shelterbackend.user.User;
import pl.shelter.shelterbackend.user.UserRole;
import pl.shelter.shelterbackend.user.UserService;
import pl.shelter.shelterbackend.user.registration.token.RegistrationToken;
import pl.shelter.shelterbackend.user.registration.token.RegistrationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final RegistrationTokenService registrationTokenService;
    private final EmailService emailService;

    public String register(RegistrationRequest registrationRequest) {

        //token ktory wysylamy
        User newUser = new User(registrationRequest.getFirstName(), registrationRequest.getLastName(), registrationRequest.getEmail(),
                registrationRequest.getPassword(),/* request.getConfirmPassword(),*/ registrationRequest.getBirthDate(), UserRole.USER);
        String token = userService.signUpUser(newUser);

        String activateLink = "http://localhost:8081/registration/confirm?token=" + token;

        emailService.prepareRegistrationMail(registrationRequest.getEmail(), prepareEmail(registrationRequest.getFirstName(), activateLink));

        return token;
    }

    public String registerAdmin(RegistrationRequest request) {

        //token ktory wysylamy
        User newAdmin = new User(request.getFirstName(), request.getLastName(), request.getEmail(),
                request.getPassword(),/* request.getConfirmPassword(),*/ request.getBirthDate(), UserRole.ADMIN);
        String token = userService.signUpUser(newAdmin);

        String activateLink = "http://localhost:8081/registration/confirm?token=" + token;

        emailService.prepareRegistrationMail(request.getEmail(), prepareEmail(request.getFirstName(), activateLink));

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        RegistrationToken registrationToken = registrationTokenService.getRegistrationToken(token);

//        if (registrationToken.getConfirmedAt() != null) {
//            throw new IllegalStateException("email already confirmed");
//        }
        LocalDateTime expirationTime = registrationToken.getExpiresAt();

        if (expirationTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Registration token has expired");
        }

        registrationTokenService.setConfirmedAt(token, LocalDateTime.now());
        userService.enableUser(registrationToken.getUser().getEmail());
        return "Potwierdzono rejestrację! :)";
    }

    private String prepareEmail(String name, String link) {
        return "<html>\n" +
                "<head>\n" +
                "  <title>Aktywacja konta</title>\n" +
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



























