package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.example.demo.model.ActiveCode;
import com.example.demo.repository.ActiveCodeRepo;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ConcurrentHashMap<String, String> getAllActiveCode() {
        ConcurrentHashMap<String, String> results = new ConcurrentHashMap<>();
        List<ActiveCode> activecodes = activeCodeRepo.findAll();
        for (int i = 0; i < activecodes.size(); i++) {
              results.put(activecodes.get(i).getCode(),activecodes.get(i).getUser_id());
        }
        return results;
    }
}
