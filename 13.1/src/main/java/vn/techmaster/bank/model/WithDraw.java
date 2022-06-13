package vn.techmaster.bank.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class WithDraw extends Command {
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private Double amount;

    public WithDraw(User requester, Account account, Double amount){
        super(requester);
        this.account = account;
        this.amount = amount;
    }
}
