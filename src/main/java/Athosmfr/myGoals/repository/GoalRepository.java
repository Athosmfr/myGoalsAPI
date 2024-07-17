package Athosmfr.myGoals.repository;

import Athosmfr.myGoals.model.goal.Goal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Page<Goal> findByUserId(Long userId, Pageable pageable);
    Goal findByIdAndUserId(Long id, Long userId);
}
