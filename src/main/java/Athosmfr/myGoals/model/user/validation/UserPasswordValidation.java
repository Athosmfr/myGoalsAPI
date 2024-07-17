package Athosmfr.myGoals.model.user.validation;

import Athosmfr.myGoals.exception.InvalidPasswordException;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordValidation {

    public void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long.");
        }
    }

}
