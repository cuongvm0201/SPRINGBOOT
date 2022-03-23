package vn.techmaster.myfirstweb.model;

import java.util.Random;

public class Cadao {
    private static final String [] tucnguc = {"Kiến tha lâu đầy tổ","Có công mài sắt, có ngày nên kim","Không thầy đố mày làm nên","Học thầy không tày học bạn"};
    private static Random generator = new Random();
    public static String randomTucngu(){
        StringBuilder sb = new StringBuilder();
        sb.append(tucnguc[generator.nextInt(0,3)]);
        return sb.toString();
    }

}
