package Athosmfr.myGoals.model.goal.validation;

import Athosmfr.myGoals.exception.InvalidDateException;
import Athosmfr.myGoals.model.goal.dto.GoalRegisterDTO;
import Athosmfr.myGoals.model.goal.dto.GoalUpdateDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GoalDateValidator {

    public void validate(GoalRegisterDTO goalRegisterDTO) {
        LocalDateTime localNowTime = LocalDateTime.now();

        if (goalRegisterDTO.startDate().isBefore(localNowTime)) {
            throw new InvalidDateException("The start date cannot be before the current date.");
        }

        if (goalRegisterDTO.endDate().isBefore(localNowTime)) {
            throw new InvalidDateException("The end date cannot be before the current date.");
        }

        if (goalRegisterDTO.endDate().isBefore(goalRegisterDTO.startDate())) {
            throw new InvalidDateException("The end date cannot be before the start date.");
        }

    }

    public void validate(GoalUpdateDTO goalUpdateDTO) {
        LocalDateTime localNowTime = LocalDateTime.now();

        if (goalUpdateDTO.startDate().isBefore(localNowTime)) {
            throw new InvalidDateException("The start date cannot be before the current date.");
        }

        if (goalUpdateDTO.endDate().isBefore(localNowTime)) {
            throw new InvalidDateException("The end date cannot be before the current date.");
        }

        if (goalUpdateDTO.endDate().isBefore(goalUpdateDTO.startDate())) {
            throw new InvalidDateException("The end date cannot be before the start date.");
        }
    }
}
