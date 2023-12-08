package pl.shelter.shelterbackend.user.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationTokenService {

    private final RegistrationTokenRepository registrationTokenRepository;

    public void saveRegistrationToken(RegistrationToken token) {
        registrationTokenRepository.save(token);
    }

    public RegistrationToken getRegistrationTokenFromDb(String token) {
        return registrationTokenRepository.findByToken(token).orElseThrow(() ->
                new IllegalStateException("Registration token was not found in the db"));
    }

    public void updateConfirmationTime(String token, LocalDateTime time) {
        RegistrationToken registrationToken = registrationTokenRepository.findByToken(token).orElseThrow();
        registrationToken.setConfirmationTime(time);
        registrationToken.setToken(token);
    }
}
