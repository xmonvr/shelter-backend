package pl.shelter.shelterbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true) //tylko do odczytu
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);    // query w bazie

    @Query("SELECT user.password FROM AppUser user WHERE user.email = ?1")
    String findPasswordByEmail(String email);

    @Query("SELECT user.id FROM AppUser user WHERE user.email = ?1")
    String findIdByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " + "SET a.enabled = TRUE WHERE a.email = ?1") //    ?1 odnosi się do pierwszego argumentu metody, czyli do email
    int enableAppUser(String email);
}
