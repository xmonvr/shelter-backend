package pl.shelter.shelterbackend.user;

import lombok.extern.slf4j.Slf4j;
import pl.shelter.shelterbackend.registration.token.ConfirmationToken;
import pl.shelter.shelterbackend.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.shelter.shelterbackend.security.config.JwtUtils;
import pl.shelter.shelterbackend.security.token.Token;
import pl.shelter.shelterbackend.security.token.TokenRepository;
import pl.shelter.shelterbackend.security.token.TokenType;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "uzytkownik o podanym mailu %s nie istnieje";
    private final AppUserRepository appUserRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final JwtUtils jwtUtils;

    @Override
    public AppUser loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        //jesli uzytkownik istnieje
        boolean userExists =  appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if (userExists) {
            //TODO if email not confirmed send confirmation email
            throw new IllegalStateException("email jest juz zajety.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        AppUser savedUser = appUserRepository.save(appUser);

        //send confirmation token
        String registrationToken = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken =
                new ConfirmationToken(registrationToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        // todo send email

        String jwtToken = jwtUtils.generateToken(appUser);
        saveToken(savedUser, jwtToken);

        return registrationToken;
    }

    public void saveToken(AppUser user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();

        tokenRepository.save(token);
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

}
