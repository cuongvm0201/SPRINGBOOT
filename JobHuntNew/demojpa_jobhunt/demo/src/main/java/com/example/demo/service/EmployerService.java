package com.example.demo.service;



import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.example.demo.model.Employer;

public interface EmployerService {
    public Collection<Employer> getAll();
    public Optional<Employer> findById(String id);
    public void updateLogo(String id,String logo_path);

    public Employer add(Employer employer);


    public void edit(Employer employer);
    public void deleteById(String id);

    ConcurrentHashMap<String, Employer> getAllEmployerHashMap();
}
