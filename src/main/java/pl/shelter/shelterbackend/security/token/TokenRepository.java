package pl.shelter.shelterbackend.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

//    @Query("SELECT token FROM Token token INNER JOIN User user ON token.user.id = user.id " +
//            "WHERE user.id = :userId and (token.expired = false)")
    List<Token> findAllValidTokenByUserId(Integer userId);

    Optional<Token> findByToken(String token);
}
