package vn.techmaster.finalproject.model;

public enum City {
    HaNoi("TP Hà nội"),
    HoChiMinh("TP Hồ Chí Minh"),
    HaiPhong("Hải phòng"),
    DaNang("Đà Nẵng"),
    BacGiang("Bắc Giang"),
    PhuYen("Phú Yên"),
    KhanhHoa("Khánh Hòa"),
    HungYen("Hưng Yên"),
    Hue("TP Huế"),
    LangSon("Lạng Sơn"),
    LaoCai("Lào Cai"),
    DienBien("Điện Biên"),
    HoaBinh("Hòa Bình"),
    BacNinh("Bắc Ninh"),
    QuangBinh("Quảng Bình"),
    KienGiang("Kiên Giang"),
    CanTho("Cần Thơ"),
    QuangNgai("Quảng Ngãi"),
    SocTrang("Sóc Trăng"),
    BinhDinh("Bình Định"),
    TienGiang("Tiền Giang");
  
    public final String label;
    private City(String label) {
      this.label = label;
    }
}
