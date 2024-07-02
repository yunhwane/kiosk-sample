package com.example.cafekiosksample.unit;

import com.example.cafekiosksample.unit.beverages.Americano;
import com.example.cafekiosksample.unit.beverages.Latte;
import net.bytebuddy.description.type.TypeDescription;
import org.junit.jupiter.api.Test;

import javax.swing.text.Style;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CafeKioskTest {

    @Test
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println("담긴 음료수 개수 ? : " + cafeKiosk.getCart().size());
        System.out.println("담긴 음료수 이름 ? : " + cafeKiosk.getCart().get(0).name());
    }

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThat(cafeKiosk.getCart()).hasSize(1);
        assertThat(cafeKiosk.getCart().get(0)).isEqualTo(americano);
    }

    @Test
    void add_multiple() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // happy case
        cafeKiosk.add(americano, 2);
        assertThat(cafeKiosk.getCart()).hasSize(2);
    }

    @Test
    void add_multiple_negative() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // negative case
        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity should be greater than 0");
    }



    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getCart()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getCart()).isEmpty();
    }

    @Test
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(latte);
        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getCart()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getCart()).isEmpty();
    }



}
