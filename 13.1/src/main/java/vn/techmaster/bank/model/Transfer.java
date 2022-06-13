package vn.techmaster.bank.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Transfer extends Command {
    @ManyToOne(fetch = FetchType.LAZY)
    private Account sendACc;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account receiveACc;

    private Double amount;

    public Transfer(User requester, Account sendACc, Account receiveACc , Double amount){
        super(requester);
        this.sendACc = sendACc;
        this.receiveACc = receiveACc;
        this.amount = amount;
    }
}
