package com.example.cafekiosksample.unit;

import com.example.cafekiosksample.unit.beverages.Beverage;
import com.example.cafekiosksample.unit.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CafeKiosk {

    private final List<Beverage> cart = new ArrayList<>();

    public void add(Beverage beverage) {
        cart.add(beverage);
        System.out.println(beverage.name() + " added to the cart");
    }

    public void remove(Beverage beverage) {
        cart.remove(beverage);
        System.out.println(beverage.name() + " removed from the cart");
    }

    public void clear() {
        cart.clear();
        System.out.println("Cart cleared");
    }


    public int calculateTotalCost() {
        int totalCost = 0;

        for (Beverage beverage : cart) {
            totalCost += beverage.cost();
        }

        return totalCost;

    }

    public Order checkout() {
        Order order = new Order(LocalDateTime.now(), cart);
        System.out.println("Order placed at " + order.getOrderTime());
        return order;
    }

    public List<Beverage> getCart() {
        return cart;
    }
}
