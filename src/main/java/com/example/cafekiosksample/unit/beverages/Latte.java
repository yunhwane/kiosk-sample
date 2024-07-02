package com.example.cafekiosksample.unit.beverages;

import com.example.cafekiosksample.unit.beverages.Beverage;

public class Latte implements Beverage {

    @Override
    public String name() {
        return "Latte";
    }

    @Override
    public int cost() {
        return 5000;
    }
}
