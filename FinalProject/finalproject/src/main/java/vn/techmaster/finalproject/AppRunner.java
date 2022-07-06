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
        .city(City.HoChiMinh)
        .name("Căn hộ chung cư tại Vinhomes Golden River Ba Son")
        .description("Cho thuê gấp căn hộ Vinhomes Golden River, O2 Tôn Đức Thắng, P Bến Nghé, Quận 1. TP. HCM."
         +"\n" 
         + "Căn hộ 02 phòng ngủ (75 - 100m2)"
          +"\n" + ".Full nội thất")
        .typeHouse(TypeHouse.STAR4)
        .address("O2 Tôn Đức Thắng, P Bến Nghé, Quận 1.")
        .price(1000000L)
        .logo_main("logomain-nha1.jpg")
        .logo_sub_main1("logosubmain1-nha1.jpg")
        .logo_sub_main2("logosubmain2-nha1.jpg")
        .logo_sub_main3("logosubmain3-nha1.jpg")
        .creatAt(LocalDateTime.now())
        .build();

        House house2 = House.builder()
        .id("1002")
        .city(City.HoChiMinh)
        .name("Căn hộ chung cư tại Vinhomes Grand Park")
        .description("CHO THUÊ CĂN HỘ CAO CẤP TẠI VINHOME GRAND PARK"
        +"\n" 
        + "2 Phòng Ngủ, 1 WC."
         +"\n" + ".Full nội thất")
        .typeHouse(TypeHouse.STAR4)
        .address("Chung cư Grand Park Quận 9, Hồ Chí Minh")
        .price(1500000L)
        .logo_main("logomain-nha2.jpeg")
        .logo_sub_main1("logosubmain1-nha2.jpeg")
        .logo_sub_main2("logosubmain2-nha2.jpeg")
        .logo_sub_main3("logosubmain3-nha2.jpeg")
        .creatAt(LocalDateTime.now())
        .build();

        House house3 = House.builder()
        .id("1003")
        .city(City.HoChiMinh)
        .name("Căn hộ chung cư tại Masteri Thảo Điền")
        .description("CHO THUÊ CĂN HỘ CAO CẤP MASTERI THẢO ĐIỀN"
        +"\n" 
        + "Căn hộ Masteri 2 phòng ngủ, diện tích 65m2 - 72m2."
         +"\n" + ".Full nội thất")
        .typeHouse(TypeHouse.STAR4)
        .address("Chung cư Masteri Thảo Điền Quận 2, Hồ Chí Minh")
        .price(1500000L)
        .logo_main("logomain-nha3.jpg")
        .logo_sub_main1("logosubmain1-nha3.jpg")
        .logo_sub_main2("logosubmain2-nha3.jpg")
        .logo_sub_main3("logosubmain3-nha3.jpg")
        .creatAt(LocalDateTime.now())
        .build();

        House house4 = House.builder()
        .id("1004")
        .city(City.HoChiMinh)
        .name("Căn hộ chung cư tại Sunwah Pearl")
        .description("CĂN GÓC 3PN VIEW SÔNG TRỰC DIỆN, GIÁ THUÊ CỰC SỐC"
        +"\n" 
        + "Kết cấu: 3Phòng Ngủ - 2WC."
        +"\n" 
        + "Diện tích: 128.15 m2."
         +"\n" + ".Full nội thất")
        .typeHouse(TypeHouse.STAR5)
        .address("90 Nguyễn Hữu Cảnh, P.22, Q.Bình Thạnh, Tp.HCM")
        .price(2000000L)
        .logo_main("logomain-nha4.jpg")
        .logo_sub_main1("logosubmain1-nha4.jpg")
        .logo_sub_main2("logosubmain2-nha4.jpg")
        .logo_sub_main3("logosubmain3-nha4.jpg")
        .creatAt(LocalDateTime.now())
        .build();

        House house5 = House.builder()
        .id("1005")
        .city(City.HaNoi)
        .name("Căn hộ chung cư tại Times City")
        .description("CĂN THUÊ MỚI NHẤT TIMES CITY THÁNG 7/2022"
        +"\n" 
        + "Căn hộ 2 Phòng Ngủ: 80m2."
         +"\n" + ".Full nội thất")
        .typeHouse(TypeHouse.STAR4)
        .address("Khu đô thị Vinhomes Times City, 458 Minh Khai, HBT, HN")
        .price(1000000L)
        .logo_main("logomain-nha5.jpg")
        .logo_sub_main1("logosubmain1-nha5.jpg")
        .logo_sub_main2("logosubmain2-nha5.jpg")
        .logo_sub_main3("logosubmain3-nha5.jpg")
        .creatAt(LocalDateTime.now())
        .build();

        House house6 = House.builder()
        .id("1006")
        .city(City.HaNoi)
        .name("Căn hộ chung cư tại Goldmark City")
        .description("CĂN HỘ 2 Phòng Ngủ CHO THUÊ TẠI TÒA SAPPHIRE GIÁ RẺ NHẤT THỊ TRƯỜNG."
        +"\n" 
        + "Căn hộ loại 2 Phòng Ngủ diện tích rộng 75m2."
         +"\n" + "Full nội thất")
        .typeHouse(TypeHouse.STAR4)
        .address("136 Hồ Tùng Mậu, Phú Diễn, Bắc Từ Liêm, Hà Nội.")
        .price(1200000L)
        .logo_main("logomain-nha6.jpg")
        .logo_sub_main1("logosubmain1-nha6.jpg")
        .logo_sub_main2("logosubmain2-nha6.jpg")
        .logo_sub_main3("logosubmain3-nha6.jpg")
        .creatAt(LocalDateTime.now())
        .build();

        House house7 = House.builder()
        .id("1007")
        .city(City.HaNoi)
        .name("Căn hộ chung cư D’. El Dorado")
        .description("TÒA NHÀ D'EL DORADO HỖ TRỢ CHO THUÊ CĂN HỘ 2 Phòng Ngủ"
        +"\n" 
        + "Căn hộ diện tích 59m2 - 92m2: 2 phòng ngủ, 2 WC."
         +"\n" + "Full nội thất")
        .typeHouse(TypeHouse.STAR4)
        .address("659A, Đường Lạc Long Quân, Phường Phú Thượng, Tây Hồ, Hà Nội")
        .price(1500000L)
        .logo_main("logomain-nha7.jpg")
        .logo_sub_main1("logosubmain1-nha7.jpg")
        .logo_sub_main2("logosubmain2-nha7.jpg")
        .logo_sub_main3("logosubmain3-nha7.jpg")
        .creatAt(LocalDateTime.now())
        .build();

        House house8 = House.builder()
        .id("1008")
        .city(City.HaNoi)
        .name("Căn hộ chung cư tại Times City ParkHill")
        .description("CĂN HỘ CHO THUÊ TẠI TIMES CITY - PARK HILL"
        +"\n" 
        + "Căn hộ 2 phòng ngủ - Diện tích: 83m2 - 87m2."
         +"\n" + "Full nội thất")
        .typeHouse(TypeHouse.STAR4)
        .address("L2 T11 OF-05 Khu đô thị Vinhomes Times City, 458 Minh Khai, HBT, HN")
        .price(1500000L)
        .logo_main("logomain-nha8.jpg")
        .logo_sub_main1("logosubmain1-nha8.jpg")
        .logo_sub_main2("logosubmain2-nha8.jpg")
        .logo_sub_main3("logosubmain3-nha8.jpg")
        .creatAt(LocalDateTime.now())
        .build();


        houseRepo.save(house1);
        houseRepo.save(house2);
        houseRepo.save(house3);
        houseRepo.save(house4);
        houseRepo.save(house5);
        houseRepo.save(house6);
        houseRepo.save(house7);
        houseRepo.save(house8);
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
