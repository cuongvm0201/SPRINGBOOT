package vn.techmaster.bank.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Pay extends Command {
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    
    private Double amount;
    private PayType payType;

    public Pay(User requester, Account account, PayType payType, Double amount){
        super(requester);
        this.account = account;
        this.payType = payType;
        this.amount = amount;
    }
}
