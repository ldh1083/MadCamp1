package com.example.test.ui.main;

public class Food {
    private String name;
    private String kcal;
    private int num;
    private int order;

    public Food (String name, String kcal, int num, int order) {
        this.name = name;
        this.kcal = kcal;
        this.num = num;
        this.order = order;
    }
    public String getName() {
        return name;
    }
    public String getkcal() {
        return kcal;
    }
    public int getNum() {
        return num;
    }
    public int getOrder() {
        return order;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNumber(String kcal) {
        this.kcal = kcal;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public void setOrder(int order) {
        this.order = order;
    }
}
