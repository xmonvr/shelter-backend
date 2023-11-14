package pl.shelter.shelterbackend.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.shelter.shelterbackend.user.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class JWTService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private Claims extractToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = extractToken(token);
        return claims.getSubject();
    }

    public Date getExpirationFromToken(String token) {
        final Claims claims = extractToken(token);
        return claims.getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        Date now = new Date();
        return getExpirationFromToken(token).before(now);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user);
    }

//    public String generateRefreshToken(User user) {
//        return createToken(new HashMap<>(), user);
//    }

    private String createToken(Map<String, Object> claims, User user) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))      //24h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Boolean isTokenValid(String token, User user) {
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
}