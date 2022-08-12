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
    @Autowired private UserRepo userRepo;
    @Autowired private HouseRepo houseRepo;
    @Autowired private ReverseRepo reverseRepo;
    @Autowired private BillRepo billRepo;
    @Autowired private Hashing hashing;
    private void generateData() {
        // Discount discount1 = new Discount("101",DiscountType.FULL_INFO,0.9);
        // Discount discount2 = new Discount("102",DiscountType.LONG_TIME,0.8);
        // Discount discount3 = new Discount("103",DiscountType.LOCAL_PEOPLE,0.7);
        // discountRepo.save(discount1);
        // discountRepo.save(discount2);
        // discountRepo.save(discount3);
        // User user1 = User.builder()
        // .id("001")
        // .fullname("Vu Manh Cuong")
        // .email("vmcuong9999@gmail.com")
        // .hashed_password(hashing.hashPassword("123456"))
        // .role(Roles.MEMBER)
        // .state(State.ACTIVE)
        // .mobile("0945940246")
        // .address("so 6 ngach 112/3 thanh nhan")
        // .wallet(10000000L)
        // .city(City.HaNoi)
        // .creatAt(LocalDateTime.now())
        // .build();

        // User user2 = User.builder()
        // .id("002")
        // .fullname("Vu Manh Cuong Admin")
        // .email("cuong_admin@gmail.com")
        // .hashed_password(hashing.hashPassword("123456"))
        // .role(Roles.ADMIN)
        // .state(State.ACTIVE)
        // .mobile("0945940246")
        // .address("bon be la nha")
        // .wallet(0L)
        // .city(City.HaNoi)
        // .creatAt(LocalDateTime.now())
        // .build();

        // User user3 = User.builder()
        // .id("003")
        // .fullname("Admin ABC")
        // .email("abc_admin@gmail.com")
        // .hashed_password(hashing.hashPassword("123456"))
        // .role(Roles.ADMIN)
        // .state(State.ACTIVE)
        // .mobile("0945940246")
        // .address("Hai Bà Trưng, Hà Nội")
        // .wallet(0L)
        // .city(City.HaNoi)
        // .creatAt(LocalDateTime.now())
        // .build();

        // userRepo.save(user1);
        // userRepo.save(user2);
        // userRepo.save(user3);
        // House house1 = House.builder()
        // .id("1001")
        // .city(City.HoChiMinh)
        // .name("Căn hộ chung cư tại Vinhomes Golden River Ba Son")
        // .description("Cho thuê gấp căn hộ Vinhomes Golden River, O2 Tôn Đức Thắng, P Bến Nghé, Quận 1. TP. HCM."
        //  +"\n" 
        //  + "Căn hộ 02 phòng ngủ (75 - 100m2)"
        //   +"\n" + ".Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("O2 Tôn Đức Thắng, P Bến Nghé, Quận 1.")
        // .price(1000000L)
        // .logo_main("logomain-nha1.jpg")
        // .logo_sub_main1("logosubmain1-nha1.jpg")
        // .logo_sub_main2("logosubmain2-nha1.jpg")
        // .logo_sub_main3("logosubmain3-nha1.jpg")
        // .view(190L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house2 = House.builder()
        // .id("1002")
        // .city(City.HoChiMinh)
        // .name("Căn hộ chung cư tại Vinhomes Grand Park")
        // .description("CHO THUÊ CĂN HỘ CAO CẤP TẠI VINHOME GRAND PARK"
        // +"\n" 
        // + "2 Phòng Ngủ, 1 WC."
        //  +"\n" + ".Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Chung cư Grand Park Quận 9, Hồ Chí Minh")
        // .price(1500000L)
        // .logo_main("logomain-nha2.jpeg")
        // .logo_sub_main1("logosubmain1-nha2.jpeg")
        // .logo_sub_main2("logosubmain2-nha2.jpeg")
        // .logo_sub_main3("logosubmain3-nha2.jpeg")
        // .view(205L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house3 = House.builder()
        // .id("1003")
        // .city(City.HoChiMinh)
        // .name("Căn hộ chung cư tại Masteri Thảo Điền")
        // .description("CHO THUÊ CĂN HỘ CAO CẤP MASTERI THẢO ĐIỀN"
        // +"\n" 
        // + "Căn hộ Masteri 2 phòng ngủ, diện tích 65m2 - 72m2."
        //  +"\n" + ".Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Chung cư Masteri Thảo Điền Quận 2, Hồ Chí Minh")
        // .price(1500000L)
        // .logo_main("logomain-nha3.jpg")
        // .logo_sub_main1("logosubmain1-nha3.jpg")
        // .logo_sub_main2("logosubmain2-nha3.jpg")
        // .logo_sub_main3("logosubmain3-nha3.jpg")
        // .view(212L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house4 = House.builder()
        // .id("1004")
        // .city(City.HoChiMinh)
        // .name("Căn hộ chung cư tại Sunwah Pearl")
        // .description("CĂN GÓC 3PN VIEW SÔNG TRỰC DIỆN, GIÁ THUÊ CỰC SỐC"
        // +"\n" 
        // + "Kết cấu: 3Phòng Ngủ - 2WC."
        // +"\n" 
        // + "Diện tích: 128.15 m2."
        //  +"\n" + ".Full nội thất")
        // .typeHouse(TypeHouse.STAR5)
        // .address("90 Nguyễn Hữu Cảnh, P.22, Q.Bình Thạnh, Tp.HCM")
        // .price(2000000L)
        // .logo_main("logomain-nha4.jpg")
        // .logo_sub_main1("logosubmain1-nha4.jpg")
        // .logo_sub_main2("logosubmain2-nha4.jpg")
        // .logo_sub_main3("logosubmain3-nha4.jpg")
        // .view(201L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house5 = House.builder()
        // .id("1005")
        // .city(City.HaNoi)
        // .name("Căn hộ chung cư tại Times City")
        // .description("CĂN THUÊ MỚI NHẤT TIMES CITY THÁNG 7/2022"
        // +"\n" 
        // + "Căn hộ 2 Phòng Ngủ: 80m2."
        //  +"\n" + ".Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Khu đô thị Vinhomes Times City, 458 Minh Khai, HBT, HN")
        // .price(1000000L)
        // .logo_main("logomain-nha5.jpg")
        // .logo_sub_main1("logosubmain1-nha5.jpg")
        // .logo_sub_main2("logosubmain2-nha5.jpg")
        // .logo_sub_main3("logosubmain3-nha5.jpg")
        // .view(185L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house6 = House.builder()
        // .id("1006")
        // .city(City.HaNoi)
        // .name("Căn hộ chung cư tại Goldmark City")
        // .description("CĂN HỘ 2 Phòng Ngủ CHO THUÊ TẠI TÒA SAPPHIRE GIÁ RẺ NHẤT THỊ TRƯỜNG."
        // +"\n" 
        // + "Căn hộ loại 2 Phòng Ngủ diện tích rộng 75m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("136 Hồ Tùng Mậu, Phú Diễn, Bắc Từ Liêm, Hà Nội.")
        // .price(1200000L)
        // .logo_main("logomain-nha6.jpg")
        // .logo_sub_main1("logosubmain1-nha6.jpg")
        // .logo_sub_main2("logosubmain2-nha6.jpg")
        // .logo_sub_main3("logosubmain3-nha6.jpg")
        // .view(246L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house7 = House.builder()
        // .id("1007")
        // .city(City.HaNoi)
        // .name("Căn hộ chung cư D’. El Dorado")
        // .description("TÒA NHÀ D'EL DORADO HỖ TRỢ CHO THUÊ CĂN HỘ 2 Phòng Ngủ"
        // +"\n" 
        // + "Căn hộ diện tích 59m2 - 92m2: 2 phòng ngủ, 2 WC."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("659A, Đường Lạc Long Quân, Phường Phú Thượng, Tây Hồ, Hà Nội")
        // .price(1500000L)
        // .logo_main("logomain-nha7.jpg")
        // .logo_sub_main1("logosubmain1-nha7.jpg")
        // .logo_sub_main2("logosubmain2-nha7.jpg")
        // .logo_sub_main3("logosubmain3-nha7.jpg")
        // .view(255L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house8 = House.builder()
        // .id("1008")
        // .city(City.HaNoi)
        // .name("Căn hộ chung cư tại Times City ParkHill")
        // .description("CĂN HỘ CHO THUÊ TẠI TIMES CITY - PARK HILL"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 83m2 - 87m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("L2 T11 OF-05 Khu đô thị Vinhomes Times City, 458 Minh Khai, HBT, HN")
        // .price(1500000L)
        // .logo_main("logomain-nha8.jpg")
        // .logo_sub_main1("logosubmain1-nha8.jpg")
        // .logo_sub_main2("logosubmain2-nha8.jpg")
        // .logo_sub_main3("logosubmain3-nha8.jpg")
        // .view(275L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house9 = House.builder()
        // .id("1009")
        // .city(City.DaNang)
        // .name("Căn hộ chung cư tại Căn hộ cao cấp Azura")
        // .description("CĂN HỘ CAO CẤP AZURA ĐÀ NẴNG CHO THUÊ, HIỆN ĐẠI, NẰM Ở TẦNG CAO"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 100m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Dự án Azura, Đường Trần Hưng Đạo, Phường An Hải Bắc, Sơn Trà, Đà Nẵng")
        // .price(1200000L)
        // .logo_main("logomain-nha9.jpg")
        // .logo_sub_main1("logosubmain1-nha9.jpg")
        // .logo_sub_main2("logosubmain2-nha9.jpg")
        // .logo_sub_main3("logosubmain3-nha9.jpg")
        // .view(345L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house10 = House.builder()
        // .id("1010")
        // .city(City.DaNang)
        // .name("Căn hộ chung cư tại The Monarchy")
        // .description("Đất Vàng Luxury cho thuê căn hộ Monarchy - View sông Hàn"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 70-90m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Dự án The Monarchy, Đường An Trung 2, Phường An Hải Tây, Sơn Trà, Đà Nẵng")
        // .price(1000000L)
        // .logo_main("logomain-nha10.jpg")
        // .logo_sub_main1("logosubmain1-nha10.jpg")
        // .logo_sub_main2("logosubmain2-nha10.jpg")
        // .logo_sub_main3("logosubmain3-nha10.jpg")
        // .view(289L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house11 = House.builder()
        // .id("1011")
        // .city(City.DaNang)
        // .name("Căn hộ chung cư tại Hoàng Anh Gia Lai Lake View Residence")
        // .description("Cho thuê căn hộ chung cư tại Hoàng Anh Gia Lai Lake View Residence"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 94m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Dự án Hoàng Anh Lakeview Residence, Đường Hàm Nghi, Phường Thạc Gián, Thanh Khê, Đà Nẵng")
        // .price(800000L)
        // .logo_main("logomain-nha11.jpg")
        // .logo_sub_main1("logosubmain1-nha11.jpg")
        // .logo_sub_main2("logosubmain2-nha11.jpg")
        // .logo_sub_main3("logosubmain3-nha11.jpg")
        // .view(234L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house12 = House.builder()
        // .id("1012")
        // .city(City.DaNang)
        // .name("Căn hộ chung cư tại Khu căn hộ F.Home")
        // .description("Cho thuê căn hộ F.Home nằm ngay vị trí trung tâm"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 70m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Dự án F.Home, Phố Lý Thường Kiệt, Phường Thạch Thang, Hải Châu, Đà Nẵng")
        // .price(800000L)
        // .logo_main("logomain-nha12.jpg")
        // .logo_sub_main1("logosubmain1-nha12.jpg")
        // .logo_sub_main2("logosubmain2-nha12.jpg")
        // .logo_sub_main3("logosubmain3-nha12.jpg")
        // .view(168L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house13 = House.builder()
        // .id("1013")
        // .city(City.DaNang)
        // .name("Căn hộ chung cư tại Hiyori Garden Tower")
        // .description("Cho thuê căn hộ Hiyori - View đường Võ Văn Kiệt"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 68m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Dự án Hiyori Garden Tower, Đường Võ Văn Kiệt, Phường An Hải Đông, Sơn Trà, Đà Nẵng")
        // .price(900000L)
        // .logo_main("logomain-nha13.jpg")
        // .logo_sub_main1("logosubmain1-nha13.jpg")
        // .logo_sub_main2("logosubmain2-nha13.jpg")
        // .logo_sub_main3("logosubmain3-nha13.jpg")
        // .view(132L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house14 = House.builder()
        // .id("1014")
        // .city(City.NhaTrang)
        // .name("Căn hộ chung cư tại Mường Thanh - Nha Trang - Khánh Hòa")
        // .description("Cho thuê căn hộ Mường Thanh - Nha Trang - Khánh Hòa"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 76m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR3)
        // .address("Dự án Mường Thanh 04 Trần Phú, 04 Đường Trần Phú, Phường Xương Huân, Nha Trang, Khánh Hòa")
        // .price(600000L)
        // .logo_main("logomain-nha14.jpg")
        // .logo_sub_main1("logosubmain1-nha14.jpg")
        // .logo_sub_main2("logosubmain2-nha14.jpg")
        // .logo_sub_main3("logosubmain3-nha14.jpg")
        // .view(140L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house15 = House.builder()
        // .id("1015")
        // .city(City.NhaTrang)
        // .name("Căn hộ chung cư tại Virgo Nha Trang - Khánh Hòa")
        // .description("Cho thuê căn hộ chung cư Vigor Nha Trang Khánh Hòa"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 71m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Dự án Virgo Nha Trang, Đường Nguyễn Thị Minh Khai, Phường Tân Lập, Nha Trang, Khánh Hòa")
        // .price(800000L)
        // .logo_main("logomain-nha15.jpg")
        // .logo_sub_main1("logosubmain1-nha15.jpg")
        // .logo_sub_main2("logosubmain2-nha15.jpg")
        // .logo_sub_main3("logosubmain3-nha15.jpg")
        // .view(85L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house16 = House.builder()
        // .id("1016")
        // .city(City.NhaTrang)
        // .name("Căn hộ chung cư tại Gold Coast Nha Trang")
        // .description("Cho thuê căn hộ chung cư Gold Coast Nha Trang Khánh Hòa"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 61m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Dự án Gold Coast Nha Trang, Đường Trần Hưng Đạo, Phường Lộc Thọ, Nha Trang, Khánh Hòa")
        // .price(800000L)
        // .logo_main("logomain-nha16.jpg")
        // .logo_sub_main1("logosubmain1-nha16.jpg")
        // .logo_sub_main2("logosubmain2-nha16.jpg")
        // .logo_sub_main3("logosubmain3-nha16.jpg")
        // .view(123L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house17 = House.builder()
        // .id("1017")
        // .city(City.QuyNhon)
        // .name("Căn hộ chung cư tại Altara Residences")
        // .description("Cho thuê căn hộ cao cấp năm sao Altara Residences view biển - TP Quy Nhơn"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 64m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR3)
        // .address("Altara Residences, 76, Đường Trần Hưng Đạo, Phường Hải Cảng, Quy Nhơn, Bình Định")
        // .price(800000L)
        // .logo_main("logomain-nha17.jpeg")
        // .logo_sub_main1("logosubmain1-nha17.jpeg")
        // .logo_sub_main2("logosubmain2-nha17.jpeg")
        // .logo_sub_main3("logosubmain3-nha17.jpeg")
        // .view(148L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house18 = House.builder()
        // .id("1018")
        // .city(City.QuyNhon)
        // .name("Căn hộ chung cư tại FLC SeaTower")
        // .description("Cho thuê căn hộ chung cư FLC SeaTower - TP Quy Nhơn"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 67m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR3)
        // .address("Dự án FLC SeaTower, Đường An Dương Vương, Phường Nguyễn Văn Cừ, Quy Nhơn, Bình Định")
        // .price(800000L)
        // .logo_main("logomain-nha18.jpeg")
        // .logo_sub_main1("logosubmain1-nha18.jpeg")
        // .logo_sub_main2("logosubmain2-nha18.jpeg")
        // .logo_sub_main3("logosubmain3-nha18.jpeg")
        // .view(88L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house19 = House.builder()
        // .id("1019")
        // .city(City.QuyNhon)
        // .name("Căn hộ chung cư cao cấp tại FLC SeaTower")
        // .description("Cho thuê căn hộ du lịch FLC Sea Tower view biển - TP Quy Nhơn"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 97m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Dự án FLC SeaTower, Đường An Dương Vương, Phường Nguyễn Văn Cừ, Quy Nhơn, Bình Định")
        // .price(1500000L)
        // .logo_main("logomain-nha19.jpeg")
        // .logo_sub_main1("logosubmain1-nha19.jpeg")
        // .logo_sub_main2("logosubmain2-nha19.jpeg")
        // .logo_sub_main3("logosubmain3-nha19.jpeg")
        // .view(111L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house20 = House.builder()
        // .id("1020")
        // .city(City.QuyNhon)
        // .name("Căn hộ chung cư tại Altara Residences")
        // .description("Cho thuê căn hộ cao cấp Altara Residences, Quy Nhơn, Bình Định"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 70m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Dự án Altara Residences, Đường Trần Hưng Đạo, Phường Hải Cảng, Quy Nhơn, Bình Định")
        // .price(1000000L)
        // .logo_main("logomain-nha20.jpg")
        // .logo_sub_main1("logosubmain1-nha20.jpg")
        // .logo_sub_main2("logosubmain2-nha20.jpg")
        // .logo_sub_main3("logosubmain3-nha20.jpg")
        // .view(132L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // House house21 = House.builder()
        // .id("1021")
        // .city(City.QuyNhon)
        // .name("Căn hộ chung cư tại Phú Tài Residence")
        // .description("Cho thuê chung cư Phú Tài Residence Quy Nhơn 2PN, Quy Nhơn, Bình Định"
        // +"\n" 
        // + "Căn hộ 2 phòng ngủ - Diện tích: 72m2."
        //  +"\n" + "Full nội thất")
        // .typeHouse(TypeHouse.STAR4)
        // .address("Dự án Phú Tài Residence, Thành phố Quy Nhơn, Bình Định")
        // .price(800000L)
        // .logo_main("logomain-nha21.jpg")
        // .logo_sub_main1("logosubmain1-nha21.jpg")
        // .logo_sub_main2("logosubmain2-nha21.jpg")
        // .logo_sub_main3("logosubmain3-nha21.jpg")
        // .view(102L)
        // .rever(0L)
        // .creatAt(LocalDateTime.now())
        // .build();

        // houseRepo.save(house1);
        // houseRepo.save(house2);
        // houseRepo.save(house3);
        // houseRepo.save(house4);
        // houseRepo.save(house5);
        // houseRepo.save(house6);
        // houseRepo.save(house7);
        // houseRepo.save(house8);
        // houseRepo.save(house9);
        // houseRepo.save(house10);
        // houseRepo.save(house11);
        // houseRepo.save(house12);
        // houseRepo.save(house13);
        // houseRepo.save(house14);
        // houseRepo.save(house15);
        // houseRepo.save(house16);
        // houseRepo.save(house17);
        // houseRepo.save(house18);
        // houseRepo.save(house19);
        // houseRepo.save(house20);
        // houseRepo.save(house21);


        // String str1 = "2022-08-13";
        // String str2 = "2022-08-15";
        // String str3 = "2022-08-20";
        // String str4 = "2022-08-30";
        // Reverse reverse1 = new Reverse("1111",house1,user1,LocalDate.parse(str1),LocalDate.parse(str2));
        // Reverse reverse2 = new Reverse("2222",house2,user1,LocalDate.parse(str3),LocalDate.parse(str4));
        // reverseRepo.save(reverse1);
        // reverseRepo.save(reverse2);
        // house1.increment();
        // house1.increment1();
        // house2.increment();
        // house2.increment1();
        // houseRepo.save(house1);
        // houseRepo.save(house2);
        // long startDate = reverse1.getCheckin().toEpochDay();
        // long endDate = reverse1.getCheckout().toEpochDay();
        // long diffrent = endDate - startDate;

        // long startDate1 = reverse2.getCheckin().toEpochDay();
        // long endDate1 = reverse2.getCheckout().toEpochDay();
        // long diffrent1 = endDate1 - startDate1;
        
        // Bill bill1 = new Bill("1", user1, reverse1, diffrent, reverse1.getHouse().getPrice()*diffrent, LocalDateTime.now());
        // Bill bill2 = new Bill("2", user1, reverse2, diffrent1, reverse2.getHouse().getPrice()*diffrent1, LocalDateTime.now());
        // billRepo.save(bill1);
        // billRepo.save(bill2);
        
    }   

    @Override
    public void run(ApplicationArguments args) throws Exception {
       generateData();
        
    }
}
