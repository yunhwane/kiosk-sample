package com.example.cafekiosksample.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @DisplayName("재고의 수량이 제공된 수량보다 작은 지 확인한다. - true")
    @Test
    void isQuantityLessThan() {
        // given
        Stock stock = Stock.create("001", 3);
        // when
        boolean result = stock.isQuantityLessThan(5);
        // then
        assertTrue(result);
    }

    @DisplayName("재고를 주어진 개수 만큼 차감할 수 있다.")
    @Test
    void decreaseQuantity() {
        // given
        Stock stock = Stock.create("001", 3);
        // when
        stock.decreaseQuantity(2);
        // then
        assertEquals(stock.getQuantity(), 1);
    }

    @DisplayName("재고 보다 많은 수량으로 차감 시도 하는 경우 예외가 발생한다. ")
    @Test
    void decreaseQuantity_Throw() {
        // given
        Stock stock = Stock.create("001", 1);
        // then
        assertThatThrownBy(() -> stock.decreaseQuantity(2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족합니다.");

    }


}