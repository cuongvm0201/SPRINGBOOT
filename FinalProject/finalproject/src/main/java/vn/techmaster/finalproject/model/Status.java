package vn.techmaster.finalproject.model;

public enum Status {
    ACTIVE("Reversed"), DISABLE("Empty");

    public final String label;
    private Status(String label) {
      this.label = label;
    }
}
