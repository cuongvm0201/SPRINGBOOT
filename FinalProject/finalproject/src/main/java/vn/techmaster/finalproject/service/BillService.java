package vn.techmaster.finalproject.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.finalproject.exception.NotFoundException;
import vn.techmaster.finalproject.model.Bill;
import vn.techmaster.finalproject.repository.BillRepo;

@Service
public class BillService {
    @Autowired private SendBillToMailService sendBillToMailService;
    @Autowired private BillRepo billRepo;
    public Bill creatBillByUser(Bill newBill){
        long startDate = newBill.getReverse().getCheckin().toEpochDay();
        long endDate = newBill.getReverse().getCheckout().toEpochDay();
        long diffrent = endDate - startDate;
        newBill.setReverseDay(diffrent);
        newBill.setTotalPrice((newBill.getReverse().getHouse().getPrice()*newBill.getReverseDay()));
        billRepo.save(newBill);
        sendBilltoMail(newBill.getId());
        return newBill;
    }

    public void sendBilltoMail(String billID) {
        // kiểm tra id có tồn tại không
        Optional<Bill> billOptional = billRepo.findById(billID);
        if(billOptional.isEmpty()){
            throw new NotFoundException("Hóa đơn với id: " + billID + " không tồn tại");
        }
       
        String id = billOptional.get().getId();
        String mailUser = billOptional.get().getUser().getEmail();
        String userName = billOptional.get().getUser().getFullname();
        String mobile = billOptional.get().getUser().getMobile();
        String houseName = billOptional.get().getReverse().getHouse().getName();
        String houseAddress = billOptional.get().getReverse().getHouse().getAddress();
        LocalDate checkin = billOptional.get().getReverse().getCheckin();
        LocalDate checkout = billOptional.get().getReverse().getCheckout();


        //Send Mail
        sendBillToMailService.sendMail(mailUser, "Thông tin hóa đơn thuê nhà", 
        "Mã đơn hàng: " + id + "\n" +
        "Tên người đặt: " + userName + "\n" +
        "SĐT Khách Hàng: " + mobile + "\n" +
        "Tên căn nhà: " + houseName + "\n" + 
        "Địa chỉ: " + houseAddress + "\n" + 
        "Ngày Check-In: " + checkin + "\n" +
        "Ngày Check-Out: " + checkout
        );
    }
}
