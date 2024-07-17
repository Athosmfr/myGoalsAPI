package Athosmfr.myGoals.service;

import Athosmfr.myGoals.model.goal.Goal;
import Athosmfr.myGoals.model.goal.dto.GoalListDTO;
import Athosmfr.myGoals.model.goal.dto.GoalRegisterDTO;
import Athosmfr.myGoals.model.goal.dto.GoalUpdateDTO;
import Athosmfr.myGoals.model.goal.validation.GoalDateValidator;
import Athosmfr.myGoals.model.user.User;
import Athosmfr.myGoals.repository.GoalRepository;
import Athosmfr.myGoals.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service responsible for managing user goals, including CRUD operations.
 * It validates goal data and ensures actions are performed by the authenticated user via a provided token.
 * Each operation interacts with the database through the GoalRepository.
 *
 * @author Athos
 */
@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private GoalDateValidator goalDateValidator;

    @Transactional
    public Goal createGoal(GoalRegisterDTO data, String token) {
        goalDateValidator.validate(data);
        User user = authUtils.getUser(token);
        Goal goal = new Goal(data, user);
        return goalRepository.save(goal);
    }

    @Transactional(readOnly = true)
    public Page<GoalListDTO> listGoals(Pageable pageable, String token) {
        User user = authUtils.getUser(token);
        return goalRepository.findByUserId(user.getId(), pageable).map(GoalListDTO::new);
    }

    @Transactional(readOnly = true)
    public GoalListDTO listGoal(Long id, String token) {
        User user = authUtils.getUser(token);
        Goal goal = goalRepository.findByIdAndUserId(id, user.getId());
        return new GoalListDTO(goal);
    }

    @Transactional
    public Goal updateGoal(GoalUpdateDTO data, String token) {
        goalDateValidator.validate(data);
        User user = authUtils.getUser(token);
        Goal goal = goalRepository.findByIdAndUserId(data.id(), user.getId());
        goal.update(data);
        return goal;
    }

    @Transactional
    public void deleteGoal(Long id, String token) {
        User user = authUtils.getUser(token);
        Goal goal = goalRepository.findByIdAndUserId(id, user.getId());
        goalRepository.deleteById(id);
    }

}
