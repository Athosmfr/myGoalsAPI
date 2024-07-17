package Athosmfr.myGoals.model.user.dto;

import Athosmfr.myGoals.model.user.User;

import java.util.Date;

public record UserListDTO(
        Long id,

        String username,

        String name,

        String email,

        Date birth_date
) {

    public UserListDTO(User user) {
        this(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getBirthDate());
    }

}
