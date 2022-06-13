package vn.techmaster.bank.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccountSaverInfo(String accountsaverId, Double startBalance, Long months, Double rate, LocalDateTime openAt, LocalDateTime closeAt) {
    
}
