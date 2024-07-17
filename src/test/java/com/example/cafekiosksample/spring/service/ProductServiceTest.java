package com.example.cafekiosksample.spring.service;

import com.example.cafekiosksample.spring.domain.Product;
import com.example.cafekiosksample.spring.domain.ProductType;
import com.example.cafekiosksample.spring.domain.SellingStatus;
import com.example.cafekiosksample.spring.dto.ProductAddRequest;
import com.example.cafekiosksample.spring.dto.ProductResponse;
import com.example.cafekiosksample.spring.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @Test
    @DisplayName("신규 상품을 등록한다. 상품 번호는 가장 최근 상품의 상품 번호에서 1 증가한 값이다.")
    void addSellingProducts() {
        // given
        Product product1 = createProduct("001");
        Product product2 = createProduct("002");
        Product product3 = createProduct("003");
        productRepository.saveAll(List.of(product1, product2, product3));
        ProductAddRequest addRequest = createProductAddRequest();
        // when
        ProductResponse response = productService.addSellingProducts(addRequest);
        // then
        assertThat(response)
                .extracting("productNumber", "name", "price", "type", "sellingType")
                .contains("004", "아메리카노", 1500, ProductType.HANDMADE, SellingStatus.SELLING);

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(4)
                .extracting("productNumber", "name", "price", "type", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", 1500, ProductType.HANDMADE, SellingStatus.SELLING),
                        tuple("002", "아메리카노", 1500, ProductType.HANDMADE, SellingStatus.SELLING),
                        tuple("003", "아메리카노", 1500, ProductType.HANDMADE, SellingStatus.SELLING),
                        tuple("004", "아메리카노", 1500, ProductType.HANDMADE, SellingStatus.SELLING));

    }

    @Test
    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품 번호는 001이다.")
    void createProductWhenProductsIsEmpty() {
        ProductAddRequest addRequest = createProductAddRequest();
        // when
        ProductResponse response = productService.addSellingProducts(addRequest);
        // then
        assertThat(response)
                .extracting("productNumber", "name", "price", "type", "sellingType")
                .contains("001", "아메리카노", 1500, ProductType.HANDMADE, SellingStatus.SELLING);
    }

    private Product createProduct(String productNumber) {
        return Product.builder()
                .name("아메리카노")
                .price(1500)
                .type(ProductType.HANDMADE)
                .productNumber(productNumber)
                .sellingStatus(SellingStatus.SELLING)
                .build();
    }

    private ProductAddRequest createProductAddRequest() {
        return new ProductAddRequest(ProductType.HANDMADE, SellingStatus.SELLING, "아메리카노", 1500);
    }


}