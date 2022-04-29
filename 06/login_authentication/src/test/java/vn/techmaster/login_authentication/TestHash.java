package vn.techmaster.login_authentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vn.techmaster.login_authentication.hash.Hashing;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
@SpringBootTest
public class TestHash {
    @Autowired private Hashing hash;

    @Test
    public void hashPassword(){
        var passwords = List.of("abc","qwert", "ox-123", " _&?LLk12124");
        for(String password: passwords){
            String hashed_pass = hash.hashPassword(password);
            assertThat(hashed_pass).isNotNull();
        }
    }

    @Test void validatePassword(){
        var passwords = List.of("abc32234++","qwert243434", "ox-123", " _&?LLk12124");
        for(String password: passwords){
            String hashed_pass = hash.hashPassword(password);
            System.out.println(hashed_pass);
            assertThat(hash.validatePassword(password, hashed_pass)).isTrue();
        }
        assertThat(hash.validatePassword("abcd12+-", "1000:9b006ac443c82b2c739d1770527b5b72:2c6610aacf91962a94540b9ab4de82509b4b51fae7bf120b571059878eb86e06c0ce22d540d5c5d3396bb9b6dc0751b115239f8b9fed993c70638eaaf370eb33")).isFalse();
    }
}
