package vn.techmaster.bank.response;

import java.time.LocalDateTime;
import java.util.UUID;

import vn.techmaster.bank.model.AutoSaver;
import vn.techmaster.bank.model.TypeSave;

public record AccountSaverInfo(String accountsaverId, 
Double startBalance, 
Long months, 
Double rate,
TypeSave typeSave,
AutoSaver autoSaver,
LocalDateTime openAt, 
LocalDateTime closeAt) {
    
}
