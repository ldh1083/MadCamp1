package com.example.test.ui.main;

public class Day {
    private int date;
    private int kcal;

    public Day (int date, int kcal) {
        this.date = date;
        this.kcal = kcal;
    }
    public int getDate() {
        return date;
    }
    public int getkcal() {
        return kcal;
    }
    public void setdate(int date) {
        this.date = date;
    }
    public void setkcal(int kcal) {
        this.kcal = kcal;
    }
}
