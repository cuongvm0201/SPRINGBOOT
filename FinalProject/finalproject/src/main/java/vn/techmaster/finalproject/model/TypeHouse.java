package vn.techmaster.finalproject.model;

public enum TypeHouse {
    STAR3("3 SAO"),
    STAR4("4 SAO"),
    STAR5("5 SAO");
  
    public final String label;
    private TypeHouse(String label) {
      this.label = label;
    }
}
