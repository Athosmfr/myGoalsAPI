package Athosmfr.myGoals.model.user;

import Athosmfr.myGoals.model.goal.Goal;
import Athosmfr.myGoals.model.user.dto.UserRegisterDTO;
import Athosmfr.myGoals.model.user.dto.UserUpdateDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Represents a user in the application. This class introduces user variables such as name, email, username, password, sex
 * and date of birth. It implements the UserDetails interface for Spring Security integration, providing methods to retrieve
 * users roles, password, and username.
 *
 * @author AthosMFR
 */

@Table(name = "users")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private Date birthDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Goal> goals = new ArrayList<>();

    private Boolean active;

    public User(UserRegisterDTO data) {
        this.active = true;
        this.name = data.name();
        this.username = data.username();
        this.email = data.email();
        this.password = data.password();
        this.sex = data.sex();
        this.birthDate = data.birthDate();
    }

    public void update(UserUpdateDTO data) {
        if (data.username() != null) {
            this.username = data.username();
        }
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.email() != null) {
            this.email = data.email();
        }
        if (data.birthDate() != null) {
            this.birthDate = data.birthDate();
        }
    }

    public void delete() {
        this.active = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
}
