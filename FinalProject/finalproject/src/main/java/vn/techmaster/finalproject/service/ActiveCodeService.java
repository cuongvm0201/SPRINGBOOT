package vn.techmaster.finalproject.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import vn.techmaster.finalproject.model.ActiveCode;
import vn.techmaster.finalproject.repository.ActiveCodeRepo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActiveCodeService {
    private ActiveCodeRepo activeCodeRepo;
    public ActiveCode addCode(String regisCode, String user_id){
        String id = UUID.randomUUID().toString();
        ActiveCode newActive = ActiveCode.builder()
        .id(id)
        .code(regisCode)
        .user_id(user_id)
        .build();
        activeCodeRepo.save(newActive);
        return newActive;
    }

    public void removeCode(String id){

    }
    public ConcurrentHashMap<String, String> getAllActiveCode() {
        ConcurrentHashMap<String, String> results = new ConcurrentHashMap<>();
        List<ActiveCode> activecodes = activeCodeRepo.findAll();
        for (int i = 0; i < activecodes.size(); i++) {
              results.put(activecodes.get(i).getCode(),activecodes.get(i).getUser_id());
        }
        return results;
    }

    public ConcurrentHashMap<String,String> getActiveCodeByID(){
        ConcurrentHashMap<String, String> results = new ConcurrentHashMap<>();
        List<ActiveCode> activecodes = activeCodeRepo.findAll();
        for (int i = 0; i < activecodes.size(); i++) {
              results.put(activecodes.get(i).getCode(),activecodes.get(i).getId());
        }
        return results;
    }
}
