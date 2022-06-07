package vn.techmaster.demouserbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import vn.techmaster.demouserbank.request.WithDrawRequest;
import vn.techmaster.demouserbank.exception.TransferException;
import vn.techmaster.demouserbank.request.DepositRequest;
import vn.techmaster.demouserbank.request.TransferRequest;
import vn.techmaster.demouserbank.service.TransferService;

@RestController
@RequestMapping("/money")

public class MoneyController {
    @Autowired
    TransferService transferService;

    @Operation(summary = "Transfer money from AccountA to AccountB")
    @PostMapping("/transfer")
    public ResponseEntity<?> callTransfer(@RequestBody TransferRequest transferRequest) {
        if (transferRequest.sendid().equals(transferRequest.getid())) {
            throw new TransferException("Tài khoản người gửi và người nhận trùng nhau");
        }

        return ResponseEntity.ok(transferService.transferMoney(transferRequest.sendid(), transferRequest.getid(),
                transferRequest.amount()));
    }

    @Operation(summary = "Withdraw money from Account")
    @PostMapping("/withdraw")
    public ResponseEntity<?> callWithDraw(@RequestBody WithDrawRequest withDrawRequest) {

        return ResponseEntity.ok(transferService.withdrawMoney(withDrawRequest.accountId(), withDrawRequest.amount()));
    }

    @Operation(summary = "Deposit money to Account")
    @PostMapping("/deposit")
    public ResponseEntity<?> callDeposit(@RequestBody DepositRequest depositRequest) {

        return ResponseEntity.ok(transferService.depositMoney(depositRequest.accountId(), depositRequest.amount()));
    }

}
