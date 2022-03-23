package vn.techmaster.myfirstweb.model;

public class Student {
    private String name;
    private int age;
    private String address;
    private String mobile;
    private String email;
    public Student(String name, int age, String address, String mobile, String email) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
}
