package vn.techmaster.finalproject;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import vn.techmaster.finalproject.hash.Hashing;
import vn.techmaster.finalproject.model.Bill;
import vn.techmaster.finalproject.model.City;
import vn.techmaster.finalproject.model.Discount;
import vn.techmaster.finalproject.model.DiscountType;
import vn.techmaster.finalproject.model.House;
import vn.techmaster.finalproject.model.Reverse;
import vn.techmaster.finalproject.model.Roles;
import vn.techmaster.finalproject.model.State;
import vn.techmaster.finalproject.model.TypeHouse;
import vn.techmaster.finalproject.model.User;
import vn.techmaster.finalproject.repository.BillRepo;
import vn.techmaster.finalproject.repository.DiscountRepo;
import vn.techmaster.finalproject.repository.HouseRepo;
import vn.techmaster.finalproject.repository.ReverseRepo;
import vn.techmaster.finalproject.repository.UserRepo;

@Component
@Transactional
public class AppRunner implements ApplicationRunner {
    @Autowired private DiscountRepo discountRepo;
    @Autowired private UserRepo userrRepo;
    @Autowired private HouseRepo houseRepo;
    @Autowired private ReverseRepo reverseRepo;
    @Autowired private BillRepo billRepo;
    @Autowired private Hashing hashing;
    private void generateAccount() {
        Discount discount1 = new Discount("101",DiscountType.FULL_INFO,0.9);
        Discount discount2 = new Discount("102",DiscountType.LONG_TIME,0.8);
        Discount discount3 = new Discount("103",DiscountType.LOCAL_PEOPLE,0.7);
        discountRepo.save(discount1);
        discountRepo.save(discount2);
        discountRepo.save(discount3);
        User user1 = User.builder()
        .id("001")
        .fullname("Vu Manh Cuong")
        .email("vmcuong9999@gmail.com")
        .hashed_password(hashing.hashPassword("123456"))
        .role(Roles.MEMBER)
        .state(State.ACTIVE)
        .mobile("0945940246")
        .address("so 6 ngach 112/3 thanh nhan")
        .city(City.HaNoi)
        .creatAt(LocalDateTime.now())
        .build();

        User user2 = User.builder()
        .id("002")
        .fullname("Vu Manh Cuong Admin")
        .email("cuong_admin@gmail.com")
        .hashed_password(hashing.hashPassword("123456"))
        .role(Roles.ADMIN)
        .state(State.ACTIVE)
        .mobile("0945940246")
        .address("bon be la nha")
        .city(City.HaNoi)
        .creatAt(LocalDateTime.now())
        .build();

        userrRepo.save(user1);
        userrRepo.save(user2);
        House house1 = House.builder()
        .id("1001")
        .city(City.DaNang)
        .name("Nhà Cao Cấp 1")
        .typeHouse(TypeHouse.STAR4)
        .address("12 Hải Phòng, Chính Gián, Hải Châu, Đà Nẵng")
        .price(1500000L)
        .logo_main("listing-01.jpg")
        .logo_sub_main1("listing-05.jpg")
        .logo_sub_main2("listing-06.jpg")
        .creatAt(LocalDateTime.now())
        .build();

        House house2 = House.builder()
        .id("1002")
        .city(City.HaNoi)
        .name("Nhà Cao Cấp 2")
        .typeHouse(TypeHouse.STAR4)
        .address("333 Lê Duẩn, Hà Nội")
        .price(2000000L)
        .logo_main("listing-02.jpg")
        .creatAt(LocalDateTime.now())
        .build();
        houseRepo.save(house1);
        houseRepo.save(house2);
        String str1 = "2022-06-28";
        String str2 = "2022-07-04";
        List<Bill> list1 = new ArrayList<>();
        Reverse reverse1 = new Reverse("1111",user1,house1,list1,LocalDate.parse(str1),LocalDate.parse(str2));
        reverseRepo.save(reverse1);
        long startDate = reverse1.getCheckin().toEpochDay();
        long endDate = reverse1.getCheckout().toEpochDay();
        long diffrent = endDate - startDate;
        
        Bill bill1 = new Bill("1", user1, reverse1, diffrent, reverse1.getHouse().getPrice()*diffrent, LocalDateTime.now());
        list1.add(bill1);
        billRepo.save(bill1);
        
    }   

    @Override
    public void run(ApplicationArguments args) throws Exception {
       generateAccount();
        
    }
}
