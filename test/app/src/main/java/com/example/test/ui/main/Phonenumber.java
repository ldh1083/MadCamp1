package com.example.test.ui.main;

public class Phonenumber implements Comparable<Phonenumber> {
    private String name;
    private String number;
    int order;
    public Phonenumber (String name, String number, int order) {
        this.name = name;
        this.number = number;
        this.order = order;
    }

    @Override
    public int compareTo(Phonenumber phonenumber) { return this.name.compareTo(phonenumber.name); }

    public String getName() {
        return name;
    }
    public String getNumber() {
        return number;
    }
    public int getOrder() {
        return order;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setOrder(int order) {
        this.order = order;
    }
}
