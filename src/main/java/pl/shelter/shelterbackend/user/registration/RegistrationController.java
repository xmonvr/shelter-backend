package pl.shelter.shelterbackend.user.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public void register(@RequestBody RegistrationRequest registrationRequest) {
        registrationService.register(registrationRequest);
    }

    @GetMapping("/confirm")
    public String confirmRegistration(@RequestParam String token) {
        return registrationService.confirmToken(token);
    }

}
