package vn.techmaster.login_authentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import vn.techmaster.login_authentication.model.State;
import vn.techmaster.login_authentication.model.User;
import vn.techmaster.login_authentication.repository.UserRepo;

import static org.assertj.core.api.Assertions.*;



@SpringBootTest
public class TestUserRepo {
    @Test
    public void addUser(){
           UserRepo userRepo = new UserRepo();
           User user = userRepo.addUser("Jone", "jone@gmail.com", "12344567", State.PENDING);
           assertThat(user).isNotNull();
           System.out.println(user.getId());
           assertThat(user.getId()).isNotBlank();
           assertThat(user.getState()).isEqualTo(State.PENDING);

    }

    @Test
    public void addUserWithDefaulteStatePending(){
           UserRepo userRepo = new UserRepo();
           User user = userRepo.addUser("Jone", "jone@gmail.com", "12344567");
           assertThat(user).isNotNull();
           assertThat(user.getId()).isNotBlank();
           assertThat(user.getState()).isEqualTo(State.PENDING);

    }

    

    @Test
    public void isEmailExist(){
        UserRepo userRepo = new UserRepo();
        userRepo.addUser("John Levy", "levy@gmail.com", "OX-133252");
        userRepo.addUser("Quốc Thái", "quocthai@gmail.com", "OX-575664");
        userRepo.addUser("Mạnh Cường", "manhcuong86@gmail.com", "OX-964t435");

        assertThat(userRepo.isEmailExist("levy@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("quocthai@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("manhcuong86@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("Manhcuong86@gmail.com")).isTrue();
        assertThat(userRepo.isEmailExist("manhcuong85@gmail.com")).isFalse();

    }

    @Test
    public void findByEmail(){
        UserRepo userRepo = new UserRepo();
        userRepo.addUser("John Levy", "levy@gmail.com", "OX-133252");
        userRepo.addUser("Quốc Thái", "quocthai@gmail.com", "OX-575664");
        userRepo.addUser("Mạnh Cường", "manhcuong86@gmail.com", "OX-964t435");
        assertThat(userRepo.findByEmail("levy@gmail.com")).isPresent();
        assertThat(userRepo.findByEmail("quocthai@gmail.com")).isPresent();
        assertThat(userRepo.findByEmail("quocthai1@gmail.com")).isNotPresent();

    }

 
}
