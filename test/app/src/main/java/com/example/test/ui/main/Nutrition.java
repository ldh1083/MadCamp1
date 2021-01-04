package com.example.test.ui.main;

public class Nutrition {
    private int carb;
    private int protein;
    private int fat;
    private int order;

    public Nutrition (int carb, int protein, int fat, int order) {
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;
        this.order = order;
    }
    public int getCarb () {
        return carb;
    }
    public int getProtein () {
        return protein;
    }
    public int getFat () {
        return fat;
    }
    public int getOrder() {
        return order;
    }
    public void setCarb(int carb) {
        this.carb = carb;
    }
    public void setProtein(int protein) {
        this.protein = protein;
    }
    public void setFat(int fat) {
        this.fat = fat;
    }
    public void setOrder(int order) {
        this.order = order;
    }
}
