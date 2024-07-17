package Athosmfr.myGoals.service;

import Athosmfr.myGoals.model.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Service responsible for generating and validating JWT tokens for user authentication.
 * It uses a secret key for signing tokens and includes methods to verify token validity
 * and retrieve claims. Tokens are set to expire after a specified duration to enhance security.
 *
 * @author Athos
 */
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("myGoals")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating token!");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("myGoals")
                    .build().verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public String getClaimFromToken(String token, String key) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("myGoals")
                    .build().verify(token)
                    .getClaim(key).toString();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid or expired Token!");
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2)
                .toInstant(ZoneOffset.of("-3"));
    }
}
