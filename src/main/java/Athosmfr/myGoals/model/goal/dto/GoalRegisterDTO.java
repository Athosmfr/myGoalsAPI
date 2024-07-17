package Athosmfr.myGoals.model.goal.dto;

import Athosmfr.myGoals.model.goal.Status;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record GoalRegisterDTO(
        @NotBlank
        @Size(min = 2, max = 150)
        String title,
        @NotBlank
        String description,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate,
        @NotNull
        Status status
) {
}
