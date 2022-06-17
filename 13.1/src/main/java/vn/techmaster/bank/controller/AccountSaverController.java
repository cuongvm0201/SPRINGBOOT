package vn.techmaster.bank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import vn.techmaster.bank.model.RateConfig;
import vn.techmaster.bank.repository.RateConfigRepo;
import vn.techmaster.bank.request.AccountSaverRequest;
import vn.techmaster.bank.request.WithDrawSaveRequest;
import vn.techmaster.bank.response.AccountSaverInfo;
import vn.techmaster.bank.service.AccountSaverService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/action")
public class AccountSaverController {
    @Autowired AccountSaverService accountSaverService;
    @Autowired RateConfigRepo rateConfigRepo;

    @Operation(summary = "Tạo một sổ tiết kiệm mới")
    @PostMapping("/open_account_saver")
    public ResponseEntity<AccountSaverInfo> openAccount(@RequestBody AccountSaverRequest accountSaverRequest) {
        AccountSaverInfo newAcc = accountSaverService.openAccount(accountSaverRequest);
        
        return ResponseEntity.ok(newAcc);
    }
    
    @Operation(summary = "Rút lãi")
    @PostMapping("/withdrawsaver")
    public ResponseEntity<String> withDrawSaverAccount(@RequestBody WithDrawSaveRequest withDrawSaveRequest){
        return ResponseEntity.ok(accountSaverService.withDrawSaveAccount(withDrawSaveRequest));
    }

    @Operation(summary = "Lấy bảng thông tin lãi suất")
    @GetMapping("/showratetable")
    public ResponseEntity<List<RateConfig>> getAllRate() {
        return ResponseEntity.ok().body(rateConfigRepo.findAll());
    }
    
    
}
