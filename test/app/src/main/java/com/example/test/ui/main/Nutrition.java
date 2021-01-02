package com.example.test.ui.main;

public class Nutrition {
    private int carb;
    private int protein;
    private int fat;

    public Nutrition (int carb, int protein, int fat) {
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;
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
    public void setCarb(int carb) {
        this.carb = carb;
    }
    public void setProtein(int protein) {
        this.protein = protein;
    }
    public void setFat(int fat) {
        this.fat = fat;
    }
}
