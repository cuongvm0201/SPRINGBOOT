package vn.techmaster.finalproject.model;

public enum PayType {
    CARD("Thẻ tín dụng"),
    WALLET("Ví");
  
    public final String label;
    private PayType(String label) {
      this.label = label;
    }
}
