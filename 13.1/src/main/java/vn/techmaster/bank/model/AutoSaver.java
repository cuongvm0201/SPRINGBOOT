package vn.techmaster.bank.model;

public enum AutoSaver {
    AUTORENEW("AUTORENEW"),
    NON_AUTORENEW("NON_AUTORENEW");
    public final String label;
    private AutoSaver(String label) {
      this.label = label;
    }
}
