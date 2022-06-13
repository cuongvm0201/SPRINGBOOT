package vn.techmaster.bank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.bank.model.AccountSaver;
import vn.techmaster.bank.request.AccountSaverRequest;
import vn.techmaster.bank.request.WithDrawSaveRequest;
import vn.techmaster.bank.response.AccountSaverInfo;
import vn.techmaster.bank.service.AccountSaverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/action")
public class AccountSaverController {
    @Autowired AccountSaverService accountSaverService;
    @PostMapping("/open_account_saver")
    public ResponseEntity<AccountSaverInfo> openAccount(@RequestBody AccountSaverRequest accountSaverRequest) {
        AccountSaverInfo newAcc = accountSaverService.openAccount(accountSaverRequest);
        
        return ResponseEntity.ok(newAcc);
    }
    
    @PostMapping("/withdrawsaver")
    public ResponseEntity<String> withDrawSaverAccount(@RequestBody WithDrawSaveRequest withDrawSaveRequest){
        return ResponseEntity.ok(accountSaverService.withDrawSaveAccount(withDrawSaveRequest));
    }
    
}
