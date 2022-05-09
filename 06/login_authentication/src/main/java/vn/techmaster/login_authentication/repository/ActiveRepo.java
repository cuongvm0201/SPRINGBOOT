package vn.techmaster.login_authentication.repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import vn.techmaster.login_authentication.model.Active;

@Repository
public class ActiveRepo {
    private ConcurrentHashMap<String, Active> saveCode = new ConcurrentHashMap<>();
    public Active addActive(String email, String active_code){
        String id = UUID.randomUUID().toString();
        Active active = Active.builder()
        .email(email)
        .active_code(active_code)
        .build();
        saveCode.put(id, active);
        return active;
    }

    public Optional<Active> findByEmail(String email){
        return saveCode.values().stream().filter(active -> active.getEmail().equalsIgnoreCase(email)).findFirst();
    }
}
