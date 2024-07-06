package com.example.cafekiosksample.spring.service;

import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.ProductType;
import com.example.cafekiosksample.spring.domain.SellingStatus;
import com.example.cafekiosksample.spring.dto.OrderCreateRequest;
import com.example.cafekiosksample.spring.dto.OrderResponse;
import com.example.cafekiosksample.spring.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("주문 리스트를 받아서 주문을 생성한다.")
    void createOrder() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1500);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3500);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 9000);

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest orderCreateRequest = createOrderCreateRequest();
        LocalDateTime registeredDateTime = LocalDateTime.now();
        // when
        OrderResponse orderResponse = orderService.createOrder(orderCreateRequest,registeredDateTime);
        // then
        assertThat(orderResponse.id()).isNotNull();
        assertThat(orderResponse)
                .extracting(
                "registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 5000);

        assertThat(orderResponse.products())
                .hasSize(2)
                .extracting("productNumber", "price")
                .contains(
                        tuple("001", 1500),
                        tuple("002", 3500)
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