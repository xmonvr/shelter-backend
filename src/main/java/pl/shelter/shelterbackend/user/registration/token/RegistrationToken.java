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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "registration_tokens")
public class RegistrationToken {

    @Id
    @SequenceGenerator(name = "registration_token_sequence", sequenceName = "registration_token_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registration_token_sequence")
    @Column(name = "registration_token_id")
    private Long id;
    @Column(nullable = false, name = "token")
    private String token;
    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "expires_at")
    private LocalDateTime expiresAt;
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;      // kiedy user potwiedzil email
    @ManyToOne      // user moze miec wiele tokenow
    @JoinColumn(nullable = false, name = "token_user_id")
    private User user;

    public RegistrationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
