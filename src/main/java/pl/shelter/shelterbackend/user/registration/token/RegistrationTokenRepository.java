package pl.shelter.shelterbackend.user.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

    Optional<RegistrationToken> findByToken(String token);

}
