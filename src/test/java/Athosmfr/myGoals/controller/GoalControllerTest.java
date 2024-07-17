package Athosmfr.myGoals.controller;

import Athosmfr.myGoals.model.goal.Status;
import Athosmfr.myGoals.model.goal.dto.GoalRegisterDTO;
import Athosmfr.myGoals.model.goal.dto.GoalUpdateDTO;
import Athosmfr.myGoals.repository.GoalRepository;
import Athosmfr.myGoals.utils.AuthUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class GoalControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<GoalRegisterDTO> goalRegisterJson;

    @Autowired
    private JacksonTester<GoalUpdateDTO> goalUpdateJson;

    @MockBean
    private GoalRepository goalRepository;

    @MockBean
    private AuthUtils authUtils;

    @Test
    @DisplayName("Should return 401 when creating goal without authentication")
    void createGoal_withoutAuthentication() throws Exception {
        var goalRegister = new GoalRegisterDTO("Goal Title", "Goal Description", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(6), Status.CREATED);

        var response = mvc.perform(post("/goal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(goalRegisterJson.write(goalRegister).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("Should return 401 when updating goal without authentication")
    void updateGoal_withoutAuthentication() throws Exception {
        var goalUpdate = new GoalUpdateDTO(1L, "Updated Title", "Updated Description", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(6), Status.IN_PROGRESS);

        var response = mvc.perform(put("/goal/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(goalUpdateJson.write(goalUpdate).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("Should return 401 when deleting goal without authentication")
    void deleteGoal_withoutAuthentication() throws Exception {
        var response = mvc.perform(delete("/goal/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @DisplayName("Should return 401 when fetching goal without authentication")
    void getGoal_withoutAuthentication() throws Exception {
        var response = mvc.perform(get("/goal/info/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
