package Athosmfr.myGoals.controller;

import Athosmfr.myGoals.model.user.User;
import Athosmfr.myGoals.model.user.dto.UserListDTO;
import Athosmfr.myGoals.model.user.dto.UserRegisterDTO;
import Athosmfr.myGoals.model.user.dto.UserUpdateDTO;
import Athosmfr.myGoals.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;


/**
 * Controller responsible for handling HTTP requests related to user account, including CRUD operations.
 * Includes the creation of the user's account which can be further used to manage the login and have access to other functions.
 * Each action requires user authentication, ensuring that only authorized users can access and modify their data.
 * An Authorization token must be provided in the request header to verify the user's identity and permissions for each action.
 *
 * @author Athos
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserListDTO> createUser(@RequestBody @Valid UserRegisterDTO data) {
        User user = userService.createAccount(data);
        return ResponseEntity.created(URI.create("/user/" + user.getId())).body(new UserListDTO(user));
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity detailUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUserInfo(token));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateUser(@RequestBody @Valid UserUpdateDTO data, @RequestHeader("Authorization") String token) {
        User updatedUser = userService.updateUserInfo(data, token);
        return ResponseEntity.ok(new UserListDTO(updatedUser));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        userService.deleteUser(token);
        return ResponseEntity.noContent().build();
    }

}
