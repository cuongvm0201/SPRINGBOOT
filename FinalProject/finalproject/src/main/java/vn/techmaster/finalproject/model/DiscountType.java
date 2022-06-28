package vn.techmaster.finalproject.model;

public enum DiscountType {
    FULL_INFO("Cập Nhật Full Info"),
    LONG_TIME("Thuê trên 10 ngày"),
    LOCAL_PEOPLE("Dân địa phương");
    public final String label;
    private DiscountType(String label) {
      this.label = label;
    }
}
