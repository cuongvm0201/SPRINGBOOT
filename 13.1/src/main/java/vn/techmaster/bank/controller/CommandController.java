package vn.techmaster.bank.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Gửi tiền vào tài khoản")
    @PostMapping("/deposit")
    public ResponseEntity<AccountInfo> deposit(@RequestBody DepositRequest depositRequest){
        
        return ResponseEntity.ok(bankService.deposit(depositRequest));
    }

    @Operation(summary = "Rút tiền")
    @PostMapping("/withdraw")
    public ResponseEntity<AccountInfo> withDraw(@RequestBody WithDrawRequest withDrawRequest){
        
        return ResponseEntity.ok(bankService.withDraw(withDrawRequest));
    }

    @Operation(summary = "Chuyển tiền từ TK-A tới TK-B")
    @PostMapping("/transfer")
    public ResponseEntity<Map<String,AccountInfo>> transfer(@RequestBody TransferRequest transferRequest) {
        
        return ResponseEntity.ok().body(bankService.transfer(transferRequest));
    }

    @Operation(summary = "Thanh toán dịch vụ")
    @PostMapping("/payment")
    public ResponseEntity<Map<String,AccountInfo>> payment(@RequestBody PayRequest payRequest) {
        
        return ResponseEntity.ok().body(bankService.payment(payRequest));
    }
        
    }
    
    

