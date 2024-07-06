package com.example.cafekiosksample.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class ProductTypeTest {


    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다. - true")
    @Test
    void containsProductType_true() {
        // given
        ProductType productType = ProductType.BOTTLE;
        // when
        boolean result = ProductType.containsProductType(productType);
        // then
        assertTrue(result);
    }
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다 - false")
    @Test
    void containsProductType_false() {
        // given
        ProductType productType = ProductType.HANDMADE;
        // when
        boolean result = ProductType.containsProductType(productType);
        // then
        assertFalse(result);
    }



}