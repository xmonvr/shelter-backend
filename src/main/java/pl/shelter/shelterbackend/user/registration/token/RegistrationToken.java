package pl.shelter.shelterbackend.user.registration.token;

import pl.shelter.shelterbackend.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "registration_tokens")
public class RegistrationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_token_id")
    private Long id;
    @Column(nullable = false, name = "token")
    private String token;
    @Column(nullable = false, name = "creation_time")
    private LocalDateTime creationTime;
    @Column(nullable = false, name = "expiration_time")
    private LocalDateTime expirationTime;
    @Column(name = "confirmation_time")
    private LocalDateTime confirmationTime;
    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public RegistrationToken(String token, LocalDateTime creationTime, LocalDateTime expirationTime, User user) {
        this.token = token;
        this.creationTime = creationTime;
        this.expirationTime = expirationTime;
        this.user = user;
    }
}
