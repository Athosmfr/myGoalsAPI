package Athosmfr.myGoals.utils;

import Athosmfr.myGoals.model.user.User;
import Athosmfr.myGoals.repository.UserRepository;
import Athosmfr.myGoals.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Utility class for handling authentication-related operations.
 * It provides methods to retrieve user information based on a given JWT token.
 * The class uses a TokenService to decode the token and fetch the corresponding user
 * from the user repository.
 *
 * @author Athos
 */
@Component
public class AuthUtils {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    public User getUser(String token) {
        return userRepository.getReferenceById(getUserId(token));
    }

    public Long getUserId(String token) {
        return Long.valueOf(tokenService.getClaimFromToken(token, "id"));
    }

}
