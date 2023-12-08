package pl.shelter.shelterbackend.user.logout;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import pl.shelter.shelterbackend.security.token.Token;
import pl.shelter.shelterbackend.security.token.TokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogOutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        final String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwtToken;
        log.info("Logout triggered");

        if (!authorizationHeader.startsWith("Bearer")) {
            log.error("Authorization header does not start with \"Bearer\"");
            return;
        }

        jwtToken = authorizationHeader.substring(7);
        Token tokenFromDb = tokenRepository.findByToken(jwtToken).orElse(null);

        if (tokenFromDb != null) {
            log.info("Logout operation on db triggered");
            tokenFromDb.setExpired(true);
            tokenRepository.save(tokenFromDb);
        }
    }
}
