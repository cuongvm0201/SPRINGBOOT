package vn.techmaster.bank.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.techmaster.bank.request.DepositRequest;
import vn.techmaster.bank.request.PayRequest;
import vn.techmaster.bank.request.TransferRequest;
import vn.techmaster.bank.request.WithDrawRequest;
import vn.techmaster.bank.response.AccountInfo;
import vn.techmaster.bank.service.BankService;

@RestController
@RequestMapping("/api")
public class CommandController {
    @Autowired BankService bankService;
    @PostMapping("/deposit")
    public ResponseEntity<AccountInfo> deposit(@RequestBody DepositRequest depositRequest){
        
        return ResponseEntity.ok(bankService.deposit(depositRequest));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountInfo> withDraw(@RequestBody WithDrawRequest withDrawRequest){
        
        return ResponseEntity.ok(bankService.withDraw(withDrawRequest));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String,AccountInfo>> transfer(@RequestBody TransferRequest transferRequest) {
        
        return ResponseEntity.ok().body(bankService.transfer(transferRequest));
    }

    @PostMapping("/payment")
    public ResponseEntity<Map<String,AccountInfo>> payment(@RequestBody PayRequest payRequest) {
        
        return ResponseEntity.ok().body(bankService.payment(payRequest));
    }
        
    }
    
    

