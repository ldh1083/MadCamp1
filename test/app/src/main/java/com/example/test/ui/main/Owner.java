package com.example.test.ui.main;

public class Owner {
    String name;
    private boolean gender;
    private int height;
    private int weight;

    public Owner (String name, boolean gender, int height, int weight) {
        this.name = name;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public String getName() {
        return name;
    }
    public boolean getGender() {
        return gender;
    }
    public int getHeight() {
        return height;
    }
    public int getWeight() {
        return weight;
    }
}