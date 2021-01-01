package com.example.test.ui.main;

public class Food {
    private String name;
    private String kcal;

    public Food (String name, String kcal) {
        this.name = name;
        this.kcal = kcal;
    }
    public String getName() {
        return name;
    }
    public String getkcal() {
        return kcal;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNumber(String number) {
        this.kcal = number;
    }
}
