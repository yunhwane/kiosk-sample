package com.example.cafekiosksample.spring.repository;

import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.ProductType;
import com.example.cafekiosksample.spring.domain.SellingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired private ProductRepository productRepository;

    @DisplayName("원하는 판매 상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn() {
        // given
        Product product1 = Product.builder()
                .name("아메리카노")
                .price(1500)
                .type(ProductType.HANDMADE)
                .productNumber("001")
                .sellingStatus(SellingStatus.SELLING)
                .build();

        Product product2 = Product.builder()
                .name("아메리카노 투샷")
                .price(3500)
                .type(ProductType.HANDMADE)
                .productNumber("002")
                .sellingStatus(SellingStatus.SELLING)
                .build();

        Product product3 = Product.builder()
                .name("팥빙수")
                .price(9000)
                .type(ProductType.HANDMADE)
                .productNumber("003")
                .sellingStatus(SellingStatus.STOP_SELLING)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SellingStatus.SELLING));
        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "price", "type", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", 1500, ProductType.HANDMADE, SellingStatus.SELLING),
                        tuple("002", "아메리카노 투샷", 3500, ProductType.HANDMADE, SellingStatus.SELLING)
                );
    }
}