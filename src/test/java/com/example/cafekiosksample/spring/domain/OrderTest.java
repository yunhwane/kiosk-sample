package com.example.cafekiosksample.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1500);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3500);

        List<Product> products = List.of(product1, product2);
        //when
        Order order = Order.create(products,LocalDateTime.now());

        //then
        assertEquals(5000, order.getTotalPrice());
    }
    @DisplayName("주문 생성 시 주문 상태는 INIT으로 생성된다.")
    @Test
    void init(){
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1500);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3500);

        List<Product> products = List.of(product1, product2);
        //when
        Order order = Order.create(products, LocalDateTime.now());
        //then
        assertEquals(OrderStatus.INIT, order.getOrderStatus());
    }

    @DisplayName("주문 생성 시 주문 등록 시간을 가진다.")
    @Test
    void registeredDateTime(){
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1500);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3500);

        List<Product> products = List.of(product1, product2);
        LocalDateTime registeredDateTime = LocalDateTime.now();
        //when
        Order order = Order.create(products, registeredDateTime);
        //then
        assertEquals(registeredDateTime, order.getRegisteredDateTime());
    }


    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .name("아메리카노")
                .price(price)
                .type(type)
                .productNumber(productNumber)
                .sellingStatus(SellingStatus.SELLING)
                .build();
    }
}