package com.example.demo.model;

public enum Skill {
    Java("Java"),
    CSharp("Csharp"),
    AWS("AWS"),
    SQL("SQL");
  
    public final String label;
    private Skill(String label) {
      this.label = label;
    }
}
