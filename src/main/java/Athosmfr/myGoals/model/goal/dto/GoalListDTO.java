package Athosmfr.myGoals.model.goal.dto;

import Athosmfr.myGoals.model.goal.Goal;
import Athosmfr.myGoals.model.goal.Status;
import java.time.LocalDateTime;

public record GoalListDTO(Long id, String name, String description, LocalDateTime startDate, LocalDateTime endDate, Status status) {

    public GoalListDTO(Goal goal) {
        this(goal.getId(), goal.getTitle(), goal.getDescription(), goal.getStartDate(), goal.getEndDate(), goal.getStatus());
    }

}
