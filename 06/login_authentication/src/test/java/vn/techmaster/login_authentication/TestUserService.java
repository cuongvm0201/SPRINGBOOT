package vn.techmaster.login_authentication;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.techmaster.login_authentication.exception.UserException;
import vn.techmaster.login_authentication.model.User;
import vn.techmaster.login_authentication.service.UserService;

@SpringBootTest
public class TestUserService {
    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        User user = userService.addUser("Vu Manh Cuong", "cuong@techmaster.vn", "abc12345");
        assertThat(user).isNotNull();
    }

    @Test
    public void login() {
        userService.addUser("Vu Manh Cuong", "cuong@techmaster.vn", "abc12345");
        assertThat(userService.login("cuong@techmaster.vn", "abc12345")).isNotNull();
        assertThat(userService.login("cuong@techmaster.vn", "abc12345+")).isNull();
    }

    @Test
    public void login_when_account_is_pending() {
        userService.addUser("Trinh Minh Cuong", "cuong@techmaster.vn", "abc1234-");
        assertThatThrownBy(() -> {
            userService.login("cuong@techmaster.vn", "abc1234-");
        }).isInstanceOf(UserException.class)
                .hasMessageContaining("User is not activated");
    }


    @Test
    public void login_when_account_is_not_found() {
        userService.addUser("Trinh Minh Cuong", "cuong@techmaster.vn", "abc1234-");
        assertThatThrownBy(() -> {
            userService.login("dung@techmaster.vn", "abc1234-");
        }).isInstanceOf(UserException.class)
                .hasMessageContaining("User is not found");
    }


    @Test
    public void login_when_password_is_incorrect() {
        userService.addUserThenAutoActivate("Trinh Minh Cuong", "cuong@techmaster.vn", "abc1234-");
        assertThatThrownBy(() -> {
            userService.login("cuong@techmaster.vn", "abc1234+");
        }).isInstanceOf(UserException.class)
                .hasMessageContaining("Password is incorrect");
    }

    @Test
    public void login_successful() {
        userService.addUserThenAutoActivate("Trinh Minh Cuong", "cuong@techmaster.vn", "abc1234-");
        User user = userService.login("cuong@techmaster.vn", "abc1234-");
        assertThat(user).isNotNull();
    }

}
