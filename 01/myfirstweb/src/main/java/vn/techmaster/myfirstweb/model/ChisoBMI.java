package vn.techmaster.myfirstweb.model;

public class ChisoBMI {
    private double height;
    private double weight;
    private double bmi;
    
    public ChisoBMI(double height, double weight, double bmi) {
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getBmi() {
        return bmi;
    }
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }
}
