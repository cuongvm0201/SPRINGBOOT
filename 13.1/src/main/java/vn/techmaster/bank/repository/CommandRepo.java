package vn.techmaster.bank.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.techmaster.bank.model.Command;

public interface CommandRepo extends JpaRepository<Command,UUID>{
    
}
