package vn.techmaster.demouserbank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import vn.techmaster.demouserbank.model.Account;
import vn.techmaster.demouserbank.repository.AccountRepo;
import vn.techmaster.demouserbank.repository.UserRepo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/account")
@Slf4j
public class AccountController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    AccountRepo accountRepo;

    @Operation(summary = "Get all accounts")
    @GetMapping
    public ResponseEntity<List<Account>> getAllAcount() {
        List<Account> allAcount = accountRepo.findAll();
        return ResponseEntity.ok().body(allAcount);
    }

    @Operation(summary = "Find account by Id")
    @GetMapping("/{id}")
    public ResponseEntity<Account> findAcountByID(@PathVariable("id") String id) {
        Account currentAccount = accountRepo.findById(id).get();
        return ResponseEntity.ok().body(currentAccount);
    }

    @Operation(summary = "Find account by User_Id")
    @GetMapping("findbyuserid/{id}")
    public ResponseEntity<?> findAcountByUserID(@PathVariable("id") String id) {
        List<Account> needAccount = userRepo.findById(id).get().getAccounts();
        return ResponseEntity.ok().body(needAccount);
    }
}
