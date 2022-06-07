package vn.techmaster.demouserbank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import vn.techmaster.demouserbank.model.User;
import vn.techmaster.demouserbank.repository.UserRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUser = userRepo.findAll();
        return ResponseEntity.ok().body(allUser);
    }

    @Operation(summary = "Find User by Id")
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserByID(@PathVariable("id") String id) {
        User currentUser = userRepo.findById(id).get();
        return ResponseEntity.ok().body(currentUser);
    }

}
