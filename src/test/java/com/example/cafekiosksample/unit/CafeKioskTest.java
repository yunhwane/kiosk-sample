package com.example.cafekiosksample.unit;

import com.example.cafekiosksample.unit.beverages.Americano;
import com.example.cafekiosksample.unit.beverages.Latte;
import com.example.cafekiosksample.unit.order.Order;
import net.bytebuddy.description.type.TypeDescription;
import org.junit.jupiter.api.Test;

import javax.swing.text.Style;

import java.time.LocalDateTime;

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

    /*
    과연 항상 영업 시간에 테스트가 통과되는가를 생각해봐야함.
    지금 오전 12시인데 영업시간이 9시부터 10시라면 테스트가 통과되지 않을 것이다.
    그러므로 영업시간을 고려한 테스트를 작성해야한다.
     */
    @Test
    void createOrderTest() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.checkout();
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0)).isEqualTo(americano);
    }

    @Test
    void createOrderWithCurrentTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.checkout(LocalDateTime.of(2024, 7, 3, 11, 30, 0));
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0)).isEqualTo(americano);
    }

    @Test
    void createOrderWithCurrentTimeClosed() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.checkout(LocalDateTime.of(2024, 7, 3, 23, 30, 0)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("The cafe is closed now");
    }

    @Test
    void calculateTotalPrice() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        assertEquals(9000, cafeKiosk.calculateTotalCost());
    }




}
