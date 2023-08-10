package pl.shelter.shelterbackend.user.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    //todo
    @GetMapping(/*path = */"/confirm")
    public String confirmRegistration(@RequestParam/*("token")*/ String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping("/register-admin")
    public String registerAdmin(@RequestBody RegistrationRequest request) {
        return registrationService.registerAdmin(request);
    }
}
