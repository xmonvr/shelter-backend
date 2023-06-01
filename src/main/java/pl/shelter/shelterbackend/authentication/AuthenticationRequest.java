package pl.shelter.shelterbackend.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AuthenticationRequest {
    private String email;
    private String password;
}
