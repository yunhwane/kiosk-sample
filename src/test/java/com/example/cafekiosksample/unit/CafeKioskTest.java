package com.example.cafekiosksample.unit;

import com.example.cafekiosksample.unit.beverages.Americano;
import org.junit.jupiter.api.Test;

import javax.swing.text.Style;

class CafeKioskTest {

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println("담긴 음료수 개수 ? : " + cafeKiosk.getCart().size());
        System.out.println("담긴 음료수 이름 ? : " + cafeKiosk.getCart().get(0).name());
    }
}
