package Athosmfr.myGoals.controller;

import Athosmfr.myGoals.model.goal.Goal;
import Athosmfr.myGoals.model.goal.dto.GoalListDTO;
import Athosmfr.myGoals.model.goal.dto.GoalRegisterDTO;
import Athosmfr.myGoals.model.goal.dto.GoalUpdateDTO;
import Athosmfr.myGoals.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * Controller responsible for handling HTTP requests related to user goal management, including CRUD operations.
 * Each operation requires the user to be authenticated, ensuring that only authorized users can access and modify their goals.
 * The Authorization token must be provided in the request header to verify the user's identity and permissions for each action.
 *
 * @author Athos
 */
@RestController
@RequestMapping("/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GoalRegisterDTO> registerGoal(@RequestBody @Valid GoalRegisterDTO data, @RequestHeader("Authorization") String token) {
        Goal goal = goalService.createGoal(data, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @GetMapping("/goals")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<GoalListDTO>> listGoals(Pageable pageable, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(goalService.listGoals(pageable, token));
    }

    @GetMapping("/goals/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> listSingleGoal(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(goalService.listGoal(id, token));
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateGoal (@RequestHeader("Authorization") String token, @RequestBody @Valid GoalUpdateDTO data) {
        Goal updatedGoal = goalService.updateGoal(data, token);
        return ResponseEntity.ok(new GoalListDTO(updatedGoal));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteGoal(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        goalService.deleteGoal(id, token);
        return ResponseEntity.noContent().build();
    }

}
