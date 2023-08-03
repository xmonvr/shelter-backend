package pl.shelter.shelterbackend.logout;

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


//    private final JwtUtils jwtUtils;
//    private final UserService appUserService;
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        final String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwtToken;

        if (!authorizationHeader.startsWith("Bearer") /*|| authorizationHeader == null*/) {
            log.error("Authorization header does not start with \"Bearer\"");
            return;
        }

        jwtToken = authorizationHeader.substring(7);
        Token tokenFromDb = tokenRepository.findByToken(jwtToken).orElse(null);

        if (tokenFromDb != null) {
            tokenFromDb.setExpired(true);
            tokenFromDb.setRevoked(true);
            tokenRepository.save(tokenFromDb);
        }
    }
}
