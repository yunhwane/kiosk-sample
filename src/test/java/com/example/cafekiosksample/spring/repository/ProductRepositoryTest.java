package com.example.cafekiosksample.spring.repository;

import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.ProductType;
import com.example.cafekiosksample.spring.domain.SellingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


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

    @DisplayName("원하는 상품 번호를 가진 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn() {
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
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));
        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "price", "type", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", 1500, ProductType.HANDMADE, SellingStatus.SELLING),
                        tuple("002", "아메리카노 투샷", 3500, ProductType.HANDMADE, SellingStatus.SELLING)
                );
    }

    @DisplayName("최신 상품 번호를 조회한다.")
    @Test
    void findLatestProduct() {
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
        String latestProduct = productRepository.findLatestProductNumber();
        // then
        assertThat(latestProduct).isEqualTo("003");
    }

    @DisplayName("가장 마지막으로 저장한 상품번호를 읽어올 때, 상품이 하나도 없는 경우 null 을 반환한다.")
    @Test
    void findLatestProduct_nothing() {
        // when
        String latestProduct = productRepository.findLatestProductNumber();
        // then
        assertThat(latestProduct).isNull();
    }
}