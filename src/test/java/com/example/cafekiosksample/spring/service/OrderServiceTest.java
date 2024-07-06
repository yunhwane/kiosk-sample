package com.example.cafekiosksample.spring.service;

import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.ProductType;
import com.example.cafekiosksample.spring.domain.SellingStatus;
import com.example.cafekiosksample.spring.dto.OrderCreateRequest;
import com.example.cafekiosksample.spring.dto.OrderResponse;
import com.example.cafekiosksample.spring.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductRepository productRepository;

    @Test
    void createOrder() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1500);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3500);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 9000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest orderCreateRequest = createOrderCreateRequest();
        // when
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest);
        // then
        assertThat(orderResponse.id()).isNotNull();
        assertThat(orderResponse)
                .extracting(
                "registeredDateTime", "totalPrice")
                .contains(LocalDateTime.now(), 5000);

        assertThat(orderResponse.products())
                .hasSize(2)
                .extracting("productNumber", "name", "price", "type")
                .contains(
                        tuple("001", "아메리카노", 1500, ProductType.HANDMADE),
                        tuple("002", "아메리카노 투샷", 3500, ProductType.HANDMADE)
                );

    }

    private OrderCreateRequest createOrderCreateRequest() {
        return new OrderCreateRequest(List.of("001", "002"));
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