package pl.shelter.shelterbackend.security.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.shelter.shelterbackend.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue
    @Column(name = "token_id")
    private Integer id;
    @Column(name = "token")
    private String token;
    @Column(name = "expired")
    private boolean expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
