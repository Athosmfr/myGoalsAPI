package Athosmfr.myGoals.model.goal.dto;

import Athosmfr.myGoals.model.goal.Status;
import Athosmfr.myGoals.model.user.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Date;

public record GoalUpdateDTO(
        @NotNull
        Long id,
        @NotBlank
        @Size(min = 2, max = 60)
        String title,
        @NotBlank
        @Size(min = 2, max = 150)
        String description,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate,
        @NotNull
        Status status
) {
}
