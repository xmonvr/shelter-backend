package pl.shelter.shelterbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true) //todo tylko do odczytu
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);    // query w bazie

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.enabled = TRUE WHERE user.email = ?1") //    ?1 odnosi się do pierwszego argumentu metody, czyli do email
    int enableUser(String email);
}
