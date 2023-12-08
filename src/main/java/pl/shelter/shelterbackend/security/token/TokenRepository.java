package pl.shelter.shelterbackend.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    List<Token> findAllValidTokenByUserId(Integer userId);

    Optional<Token> findByToken(String token);
}
