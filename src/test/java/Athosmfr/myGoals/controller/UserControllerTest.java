package Athosmfr.myGoals.controller;

import Athosmfr.myGoals.model.user.Sex;
import Athosmfr.myGoals.model.user.User;
import Athosmfr.myGoals.model.user.dto.UserRegisterDTO;
import Athosmfr.myGoals.repository.UserRepository;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<UserRegisterDTO> userRegisterJson;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Should return 201 when user is successfully registered")
    @WithMockUser
    void registerUser_validData() throws Exception {
        var userRegister = new UserRegisterDTO("tst", "test1", "test1@example.com", "12345678", Sex.MAN, new Date(90, 5, 10)); // Senha alterada

        User mockUser = new User(userRegister);
        mockUser.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        var response = mvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegisterJson.write(userRegister).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Should return 401 when accessing user info without authentication")
    void getUserInfo_withoutAuthentication() throws Exception {
        var response = mvc.perform(get("/user/info")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


    @Test
    @DisplayName("Should return 401 when trying to delete user without authentication")
    void deleteUser_withoutAuthentication() throws Exception {
        var response = mvc.perform(delete("/user/delete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

}
