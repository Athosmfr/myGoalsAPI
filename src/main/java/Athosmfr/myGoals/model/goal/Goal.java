package Athosmfr.myGoals.model.goal;

import Athosmfr.myGoals.model.goal.dto.GoalRegisterDTO;
import Athosmfr.myGoals.model.goal.dto.GoalUpdateDTO;
import Athosmfr.myGoals.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * This class represents a personal goal associated with the user. It includes details of the goal such as the title, description, dates and the current
 * status. The class provides a constructor for use on the registration and also a method to update a single goal if needed.
 *
 * @author Athos
 */
@Table(name = "goals")
@Entity(name = "Goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private User user;

    public Goal(GoalRegisterDTO data, User user) {
        this.title = data.title();
        this.description = data.description();
        this.startDate = data.startDate();
        this.endDate = data.endDate();
        this.status = data.status();
        this.user = user;
    }

    public void update(GoalUpdateDTO data) {
        if (data.title() != null) {
            this.title = data.title();
        }
        if (data.description() != null) {
            this.description = data.description();
        }
        if (data.startDate() != null) {
            this.startDate = data.startDate();
        }
        if (data.endDate() != null) {
            this.endDate = data.endDate();
        }
        if (data.status() != null) {
            this.status = data.status();
        }
    }

}
