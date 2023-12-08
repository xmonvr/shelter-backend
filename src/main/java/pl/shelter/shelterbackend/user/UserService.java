package pl.shelter.shelterbackend.user;

import lombok.extern.slf4j.Slf4j;
import pl.shelter.shelterbackend.user.registration.token.RegistrationToken;
import pl.shelter.shelterbackend.user.registration.token.RegistrationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.shelter.shelterbackend.security.config.JWTService;
import pl.shelter.shelterbackend.security.token.Token;
import pl.shelter.shelterbackend.security.token.TokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RegistrationTokenService registrationTokenService;
    private final JWTService jwtService;

    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("Uzytkownik o podanym mailu %s nie istnieje"));
    }

    public String signUpUser(User user) {
        boolean userExists =  userRepository.findByEmail(user.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("Email is already taken.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);

        String registrationToken = UUID.randomUUID().toString();
        RegistrationToken confirmationToken = new RegistrationToken(registrationToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        registrationTokenService.saveRegistrationToken(confirmationToken);

        String jwtToken = jwtService.generateToken(user);
        saveToken(savedUser, jwtToken);

        return registrationToken;
    }

    public void saveToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .build();

        tokenRepository.save(token);
    }

    public void enableUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setEnabled(true);
    }

}
