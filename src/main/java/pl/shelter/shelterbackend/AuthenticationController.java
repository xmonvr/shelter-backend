package pl.shelter.shelterbackend;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.shelter.shelterbackend.security.config.JwtUtils;
import pl.shelter.shelterbackend.user.AppUser;
import pl.shelter.shelterbackend.user.AppUserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public String authenticate(@RequestBody AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));
        // Ta linijka ustawia informacje o uwierzytelnieniu użytkownika w bieżącym kontekście SecurityContextHolder.
        // W klasie SecurityContextHolder jest przechowywana informacja o aktualnym użytkowniku i jego uwierzytelnieniu,
        // która jest przenoszona między warstwami aplikacji. Dzięki temu informacje o uwierzytelnieniu są dostępne w różnych
        // miejscach w aplikacji.
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        final AppUser user = appUserService.loadUserByUsername(request.getEmail());
        String jwt = null;
        if (user != null) {
            jwt = jwtUtils.generateToken(user);
            return jwt;
        }

        return jwt;
    }

    public Boolean isLoggedIn() {
        return null;
    }
}
