package com.example.cafekiosksample.unit.beverages;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    void name() {
        Americano americano = new Americano();
        assertEquals("Americano", americano.name());
    }

    @Test
    void cost() {
        Americano americano = new Americano();
        assertEquals(4000, americano.cost());
    }

}