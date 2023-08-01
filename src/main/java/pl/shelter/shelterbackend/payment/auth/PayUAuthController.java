package pl.shelter.shelterbackend.payment.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@Slf4j
@AllArgsConstructor
public class PayUAuthController {
//todo kontroller do testow
    private OAuthService oAuthService;

    @PostMapping("create-oauth-token")
    public String createAuth() {
        return oAuthService.preparePayUAuth();
    }
}
