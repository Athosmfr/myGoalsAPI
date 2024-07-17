package Athosmfr.myGoals.controller;


import Athosmfr.myGoals.model.user.User;
import Athosmfr.myGoals.model.user.dto.AuthenticationDTO;
import Athosmfr.myGoals.model.user.dto.JWTDTO;
import Athosmfr.myGoals.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller responsible for handling user authentication requests.
 * Provides an endpoint for user login, returning a JWT token upon successful authentication.
 *
 * @author Athos
 */
@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> requestLogin(@RequestBody @Valid AuthenticationDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new JWTDTO(tokenJWT));
    }

}
