package vn.techmaster.finalproject.model;

public enum State {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    DISABLED("DISABLED"),
    REMOVED("REMOVED");

    public final String label;
    private State(String label) {
      this.label = label;
    }
}
