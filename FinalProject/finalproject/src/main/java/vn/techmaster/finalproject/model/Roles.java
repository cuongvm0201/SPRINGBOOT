package vn.techmaster.finalproject.model;

public enum Roles {
    ADMIN("ADMIN"),
    SUBADMIN("SUBADMIN"),
    MEMBER("MEMBER");

    public final String label;
    private Roles(String label) {
      this.label = label;
    }
}