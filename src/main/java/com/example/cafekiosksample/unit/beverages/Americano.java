package com.example.cafekiosksample.unit.beverages;

public class Americano implements Beverage{
    @Override
    public String name() {
        return "Americano";
    }

    @Override
    public int cost() {
        return 4000;
    }
}
