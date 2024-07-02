package com.example.cafekiosksample.unit;

import com.example.cafekiosksample.unit.beverages.Americano;
import com.example.cafekiosksample.unit.beverages.Latte;

public class CafeKioskRunner {

    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        cafeKiosk.add(new Latte());

        int totalCost = cafeKiosk.calculateTotalCost();
        System.out.println("Total cost: " + totalCost);

    }

}
