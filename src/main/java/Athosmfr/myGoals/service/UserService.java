package Athosmfr.myGoals.service;

import Athosmfr.myGoals.model.user.User;
import Athosmfr.myGoals.model.user.dto.UserListDTO;
import Athosmfr.myGoals.model.user.dto.UserRegisterDTO;
import Athosmfr.myGoals.model.user.dto.UserUpdateDTO;
import Athosmfr.myGoals.model.user.validation.UserPasswordValidation;
import Athosmfr.myGoals.repository.UserRepository;
import Athosmfr.myGoals.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service responsible for managing user accounts, including CRUD operations.
 * It ensures password validation and handles password encryption for security.
 * User actions require authentication via a provided token to verify the user's identity.
 *
 * @author Athos
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserPasswordValidation userPasswordValidation;

    @Autowired
    private AuthUtils authUtils;


    @Transactional
    public User createAccount(UserRegisterDTO data) {
        userPasswordValidation.validatePassword(data.password());
        String encryptedPassword = passwordEncoder.encode(data.password());
        User user = new User(data);
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserListDTO getUserInfo(String token) {
        User user = authUtils.getUser(token);
        return new UserListDTO(user);
    }

    @Transactional
    public User updateUserInfo(UserUpdateDTO data, String token) {
        User user = authUtils.getUser(token);
        user.update(data);

        String currentPassword = data.currentPassword();
        String newPassword = data.newPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            if (currentPassword == null || !passwordEncoder.matches(currentPassword, user.getPassword())) {
                throw new RuntimeException("The current password provided is incorrect");
            }
            String newEncryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(newEncryptedPassword);
        }

        return user;
    }

    @Transactional
    public void deleteUser(String token) {
        User user = authUtils.getUser(token);
        user.delete();
    }

}
