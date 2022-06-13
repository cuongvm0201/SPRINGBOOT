package vn.techmaster.bank.model;

import lombok.Data;

public enum TypeSave {
    EVERYMONTH("EVERYMONTH"),
    FINAL("FINAL");
    public final String label;
    private TypeSave(String label) {
      this.label = label;
    }
}
