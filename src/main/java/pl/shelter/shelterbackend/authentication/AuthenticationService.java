package pl.shelter.shelterbackend.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.shelter.shelterbackend.security.config.JWTService;
import pl.shelter.shelterbackend.security.token.TokenRepository;
import pl.shelter.shelterbackend.user.User;
import pl.shelter.shelterbackend.user.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;


    public ResponseEntity<?> authenticate(AuthenticationRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword()
            ));
        } catch (Exception ex) {
            log.error("Authentication exception occurred: " + ex.getMessage());
            throw ex;
        }
        // Ta linijka ustawia informacje o uwierzytelnieniu użytkownika w bieżącym kontekście SecurityContextHolder.
        // W klasie SecurityContextHolder jest przechowywana informacja o aktualnym użytkowniku i jego uwierzytelnieniu,
        // która jest przenoszona między warstwami aplikacji. Dzięki temu informacje o uwierzytelnieniu są dostępne w różnych
        // miejscach w aplikacji.

        final User user = userService.loadUserByUsername(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtUtils.generateRefreshToken(user);

        //uniewazniamy wszystkie stare tokeny
        var validUserTokens = tokenRepository.findAllValidTokenByUserId(user.getId());
        if (validUserTokens.isEmpty()) {
            log.error("validUserTokens is empty");
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
        userService.saveToken(user, jwtToken);

        return ResponseEntity.ok().headers(addHeaders(jwtToken)).build();
    }

    private static HttpHeaders addHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        return headers;
    }

}
