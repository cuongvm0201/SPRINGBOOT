package vn.techmaster.myfirstweb.model;

import java.util.ArrayList;

public class ListStudent {
    static ArrayList<Student> allStudent = new ArrayList<>();
    public static ArrayList<Student> infoStudent(){
        allStudent.add(new Student("Cường",30,"Hà Nội","0945940246","vmcuong2192@gmail.com"));
        allStudent.add(new Student("Thái",24,"Hà Nội","0982516554","thai123@gmail.com"));
        allStudent.add(new Student("Khải",24,"Hà Nội","097543210","khai456@gmail.com"));
        allStudent.add(new Student("Tuấn",30,"Hà Nội","0923456789","tuan.tran222@gmail.com"));
        return allStudent;
    }


   
}
