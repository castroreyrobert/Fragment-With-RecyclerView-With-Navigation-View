package com.example.fragmentwithrecycler.models;


public class Expenses {
    long eId;
    String eName;
    double eAmount;


    public Expenses(){}
    public Expenses(long id, String name, double amount){
        this.eId = id;
        this.eName = name;
        this.eAmount = amount;
    }

    public double geteAmount() {
        return eAmount;
    }

    public void seteAmount(double eAmount) {
        this.eAmount = eAmount;
    }

    public long geteId() {
        return eId;
    }

    public void seteId(long eId) {
        this.eId = eId;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }
}
