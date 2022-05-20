package vn.techmaster.demomanageruser_jobhunt.model;

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
