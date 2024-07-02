package com.example.cafekiosksample.unit.order;

import com.example.cafekiosksample.unit.beverages.Beverage;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private final LocalDateTime orderTime;
    private final List<Beverage> beverages;

    public Order(LocalDateTime orderTime, List<Beverage> beverages) {
        this.orderTime = orderTime;
        this.beverages = beverages;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }
}
