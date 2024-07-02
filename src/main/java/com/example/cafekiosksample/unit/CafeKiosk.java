package com.example.cafekiosksample.unit;

import com.example.cafekiosksample.unit.beverages.Beverage;
import com.example.cafekiosksample.unit.order.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CafeKiosk {

    private final List<Beverage> cart = new ArrayList<>();
    public static final LocalDateTime OPENING_TIME = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 10, 0);
    public static final LocalDateTime CLOSING_TIME = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 22, 0);

    public void add(Beverage beverage) {
        cart.add(beverage);
        System.out.println(beverage.name() + " added to the cart");
    }

    public void add(Beverage beverage, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity should be greater than 0");
        }


        for (int i = 0; i < quantity; i++) {
            cart.add(beverage);
        }

        System.out.println(beverage.name() + " added to the cart " + quantity + " times");
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
        LocalDateTime currentTime = LocalDateTime.now();

        if (currentTime.isBefore(OPENING_TIME) || currentTime.isAfter(CLOSING_TIME)) {
            throw new IllegalStateException("The cafe is closed now");
        }

        Order order = new Order(currentTime, cart);
        System.out.println("Order placed at " + order.getOrderTime());
        return order;
    }

    public Order checkout(LocalDateTime currentTime) {

        if (currentTime.isBefore(OPENING_TIME) || currentTime.isAfter(CLOSING_TIME)) {
            throw new IllegalStateException("The cafe is closed now");
        }

        Order order = new Order(currentTime, cart);
        System.out.println("Order placed at " + order.getOrderTime());
        return order;
    }


    public List<Beverage> getCart() {
        return cart;
    }
}
