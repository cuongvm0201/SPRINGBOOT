package com.example.demo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.exception.UserException;
import com.example.demo.model.Roles;
import com.example.demo.model.State;
import com.example.demo.model.User;
import com.example.demo.service.EmailService;

@Repository
public interface UserRepo extends JpaRepository<User,String>{
  Optional<User> findUsersByEmail(String email);

  }



