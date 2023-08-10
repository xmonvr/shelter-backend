package pl.shelter.shelterbackend.user;

import lombok.extern.slf4j.Slf4j;
import pl.shelter.shelterbackend.registration.token.RegistrationToken;
import pl.shelter.shelterbackend.registration.token.RegistrationTokenService;
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
//    private final EmailService emailService;

    @Override
    public User loadUserByUsername(String email) /*throws UsernameNotFoundException*/ {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalStateException("Uzytkownik o podanym mailu %s nie istnieje"));
    }

    public String signUpUser(User user) {
        //jesli uzytkownik istnieje
        boolean userExists =  userRepository.findByEmail(user.getEmail()).isPresent();
//        boolean isUserEnabled = userRepository.findByEmail(user.getEmail()).orElseThrow().getEnabled();
        if (userExists) {
            //TODO if email not confirmed send confirmation email
            throw new IllegalStateException("Email is already taken.");
//            if (!isUserEnabled) {
//                emailService.prepareRegistrationMail(user.getEmail(), );
//            }
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);

        //send confirmation token
        String registrationToken = UUID.randomUUID().toString();
        RegistrationToken confirmationToken =
                new RegistrationToken(registrationToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        registrationTokenService.saveRegistrationToken(confirmationToken);
        // todo send email

        String jwtToken = jwtService.generateToken(user);
        saveToken(savedUser, jwtToken);

        return registrationToken;
    }

    public void saveToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .revoked(false)
                .expired(false)
                .build();

        tokenRepository.save(token);
    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }

}
