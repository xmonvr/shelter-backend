package pl.shelter.shelterbackend.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.shelter.shelterbackend.security.config.JwtUtils;
import pl.shelter.shelterbackend.security.token.Token;
import pl.shelter.shelterbackend.security.token.TokenRepository;
import pl.shelter.shelterbackend.security.token.TokenType;
import pl.shelter.shelterbackend.user.AppUser;
import pl.shelter.shelterbackend.user.AppUserRepository;
import pl.shelter.shelterbackend.user.AppUserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;
    private final JwtUtils jwtUtils;
    private final AppUserRepository userRepository;
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

        final AppUser user = appUserService.loadUserByUsername(request.getEmail());
        var jwtToken = jwtUtils.generateToken(user);
        var refreshToken = jwtUtils.generateRefreshToken(user);

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
        appUserService.saveToken(user, jwtToken);

        return ResponseEntity.ok().headers(addHeaders(jwtToken)).build();
    }

    private static HttpHeaders addHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        return headers;
    }

}
