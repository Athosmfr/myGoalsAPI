package Athosmfr.myGoals.model.user.dto;

import Athosmfr.myGoals.model.user.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record UserRegisterDTO(
        @NotBlank
        @Size(min = 2, max = 40)
        String username,
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotNull
        Sex sex,
        @NotNull
        Date birthDate
) {
}
