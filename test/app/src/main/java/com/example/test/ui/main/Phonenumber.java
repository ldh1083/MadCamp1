package com.example.test.ui.main;

public class Phonenumber {
    private String name;
    private String number;

    public Phonenumber (String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }
}
