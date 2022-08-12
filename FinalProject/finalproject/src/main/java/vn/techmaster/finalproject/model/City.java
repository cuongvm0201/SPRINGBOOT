package vn.techmaster.finalproject.model;

public enum City {
    HaNoi("TP Hà nội"),
    HoChiMinh("TP Hồ Chí Minh"),
    HaiPhong("Hải phòng"),
    DaNang("Đà Nẵng"),
    BacGiang("Bắc Giang"),
    TuyHoa("TP Tuy Hòa"),
    NhaTrang("TP Nha Trang"),
    DaLat("TP Đà Lạt"),
    Hue("TP Huế"),
    LangSon("Lạng Sơn"),
    LaoCai("Lào Cai"),
    DienBien("Điện Biên"),
    HoaBinh("Hòa Bình"),
    BacNinh("Bắc Ninh"),
    DongHoi("TP Đồng Hới"),
    PhuQuoc("Phú Quốc"),
    CanTho("TP Cần Thơ"),
    QuangNgai("TP Quảng Ngãi"),
    SocTrang("TP Sóc Trăng"),
    QuyNhon("TP Quy Nhơn"),
    MyTho("TP Mỹ Tho");
  
    public final String label;
    private City(String label) {
      this.label = label;
    }
}
