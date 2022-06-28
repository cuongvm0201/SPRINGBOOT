package vn.techmaster.bank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.techmaster.bank.model.Account;
import vn.techmaster.bank.model.User;
import vn.techmaster.bank.repository.AccountRepo;
import vn.techmaster.bank.repository.UserRepo;
import vn.techmaster.bank.request.AccountRequest;
import vn.techmaster.bank.response.AccountInfo;
import vn.techmaster.bank.service.AccountService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/accountmanager")
public class AccountController {
    @Autowired private UserRepo userRepo;
    @Autowired private AccountRepo accountRepo;
    @Autowired private AccountService accountService;
  
    @GetMapping("/getalluser")
    public ResponseEntity<List<User>> getAllUser() {
      return ResponseEntity.ok().body(userRepo.findAll());
      }
  
  @GetMapping("/getallaccount")
  public ResponseEntity<List<Account>> getAllAccount() {
    return ResponseEntity.ok().body(accountRepo.findAll());
    }

  @Operation(summary = "Tạo một account tại ngân hàng mới")
  @PostMapping("/createnewaccount")
  public ResponseEntity<AccountInfo> creatNewAccountBank(@RequestBody AccountRequest accountRequest) {
      return ResponseEntity.ok().body(accountService.openNewAccountBank(accountRequest));
  }
  
}
